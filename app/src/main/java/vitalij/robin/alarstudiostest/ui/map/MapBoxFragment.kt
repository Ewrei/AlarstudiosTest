package vitalij.robin.alarstudiostest.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.InfoWindowAdapter
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_map_box.*
import vitalij.robin.alarstudiostest.AlarStudiosApplication
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.observeToError
import vitalij.robin.alarstudiostest.common.extensions.observeToProgressBar
import vitalij.robin.alarstudiostest.common.extensions.setToolbarTitle
import vitalij.robin.alarstudiostest.model.network.MainModel
import vitalij.robin.alarstudiostest.ui.common.BaseFragment
import vitalij.robin.alarstudiostest.ui.map.MapBoxActivity.Companion.MAIN_MODEL
import javax.inject.Inject

private const val ZOOM = 10.0
private const val TILT = 20.0
private const val DURATION_MS_MAP_BOX = 1000

class MapBoxFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: MapBoxViewModelFactory

    private lateinit var viewModel: MapBoxViewModel

    private var mapboxMap: MapboxMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_map_box, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(MapBoxViewModel::class.java).apply {
                observeToProgressBar(this@MapBoxFragment)
                observeToError(this@MapBoxFragment)
            }

        arguments?.let {
            viewModel.mainModel = it.getSerializable(MAIN_MODEL) as MainModel
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AlarStudiosApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync(this)

        mapView?.getMapAsync { map ->
            map.setStyle(Style.LIGHT)
            setMarker(map)
            setPosition(map)
        }

        initToolbar()

        setListener()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapView?.getMapAsync { map ->
            map.uiSettings.isLogoEnabled = false
            map.uiSettings.isCompassEnabled = false
            map.uiSettings.isAttributionEnabled = false
        }
    }

    private fun initToolbar() {
        setToolbarTitle(R.string.map)
    }

    //TODO Deprecated Replace in next version
    private fun setMarker(map: MapboxMap) {
        viewModel.mainModel?.let { model ->
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(model.lat, model.lon))
                    .title(model.name)
            )
            mapboxMap?.infoWindowAdapter =
                InfoWindowAdapter {
                    val customView: View = layoutInflater.inflate(R.layout.marker_view, null)
                    customView.layoutParams =
                        FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    customView.findViewById<TextView>(R.id.id).text = model.id
                    customView.findViewById<TextView>(R.id.name).text = model.name
                    customView.findViewById<TextView>(R.id.country).text = model.country
                    customView.findViewById<TextView>(R.id.lat).text = model.lat.toString()
                    customView.findViewById<TextView>(R.id.lon).text = model.lon.toString()
                    customView
                }
        }
    }

    private fun setPosition(map: MapboxMap) {
        viewModel.mainModel?.let {
            val position = CameraPosition.Builder()
                .target(LatLng(it.lat, it.lon))
                .zoom(ZOOM)
                .tilt(TILT)
                .build()

            map.animateCamera(
                CameraUpdateFactory.newCameraPosition(position),
                DURATION_MS_MAP_BOX
            )
        }
    }

    private fun setListener() {
        closeButton.setOnClickListener {
            activity?.finish()
        }
    }

    companion object {

        fun newInstance(mainModel: MainModel) = MapBoxFragment().apply {
            arguments = Bundle().apply {
                putSerializable(MAIN_MODEL, mainModel)
            }
        }
    }
}
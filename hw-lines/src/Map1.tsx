import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    edges: Map<Number, string[]>
    // TODO: Define the props of this component. You will want to pass down edges
    // so you can render them here
}

interface MapState {}

class Map1 extends Component<MapProps, MapState> {
    renderLines() {  // used to incorporate MapLines to draw the lines on the map
        let list = []
        for(let element of this.props.edges.keys()) {
            let list1 = this.props.edges.get(element);
            if(list1 != null) {
                list.push(<MapLine key={element.toString()} color={list1[4]} x1={+list1[0]} x2={+list1[2]} y1={+list1[1]} y2={+list1[3]}
                />)
            }
        }
        return list
    }

    render() {
        return (
            <div id="map">
                <MapContainer
                    center={position}
                    zoom={15}
                    scrollWheelZoom={false}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {
                        // TODO: Render map lines here using the MapLine component. E.g.
                        // <MapLine key={key1} color="red" x1={1000} y1={1000} x2={2000} y2={2000}/>
                        // will draw a red line from the point 1000,1000 to 2000,2000 on the
                        // map
                        this.renderLines() // calls the renderLines method on the given input
                    }
                </MapContainer>
            </div>
        );
    }
}

export default Map1;

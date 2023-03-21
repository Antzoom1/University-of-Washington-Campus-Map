/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import * as Path from "path";

// implements the AppState of the Map
interface AppState {
    start: string,
    end: string,
    requestedResult: []
}

class App extends Component<{}, AppState> {
    constructor(props: {}) {
        super(props);
        this.state = {
            start: "",
            end: "",
            requestedResult: []
        };
    }

    // Sets the starting building to the target
    setStartChange = (event: any) => {
        this.setState({
            end: this.state.end, requestedResult: this.state.requestedResult,
            start: event.target.value
        });
    };

    // Sets the ending building to the target
    setEndChange = (event: any) => {
        this.setState({
            requestedResult: this.state.requestedResult, start: this.state.start,
            end: event.target.value
        });
    };

    // Sets up the server. If the response is not online, it warns with a alert.
    makingRequest = async () => {
        try {
            if(this.state.end != "" && this.state.start != "") {
                let response = await fetch( "http://localhost:4567/findPath?start=" + this.state.start);
                if(!(response.ok)) {
                    alert("Status is incorrect, Expected: 200, Was: " + response.status);
                }
                let pathWay = await response.json();
                this.setState( {
                    end: this.state.end, start: this.state.start,
                    requestedResult: pathWay
                });
            }
        } catch(event) {
            alert("There was an error in contacting the server.");
            console.log(event);
        }
    };

    // On the button clicked, it draws the graph. Oherwise, it clears
    onEventClick = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        if((event.target as HTMLElement).id === "Draw Graph") {
            let pathWay = this.makingRequest();
        } else {
            this.setState( {
                start: "",
                end: "",
                requestedResult: [],
            });
        }
    }

    render() {
        return (
            <div>
                <div>
                    <Map path = {this.state.requestedResult}/>
                </div>
                <button id = "drawGraph" onClick={(event ) =>
                {this.onEventClick(event)}}>Draw</button>
                <button id = "clearGraph" onClick={(event ) =>
                {this.onEventClick(event)}}>Clear</button>



                <select value={this.state.start} onChange={this.setStartChange}>
                    <option value=""> </option>
                    <option value="BAG"> Bagley Hall (East Entrance) </option>
                    <option value="BAG (NE)"> Bagley Hall (Northeast Entrance) </option>
                    <option value="BGR"> By George </option>
                    <option value="CSE"> Paul G. Allen Center for Computer Science & Engineering </option>
                    <option value="CS2"> Bill & Melinda Gates Center For Computer Science & Engineering </option>
                    <option value="DEN"> Denny Hall </option>
                    <option value="EEB"> Electrical Engineering Building (North Entrance) </option>
                    <option value="EEB (S)"> Electrical Engineering Building (South Entrance) </option>
                    <option value="GWN"> Gowen Hall </option>
                    <option value="KNE"> Kane Hall (North Entrance) </option>
                    <option value="KNE (E)"> Kane Hall (East Entrance) </option>
                    <option value="KNE (SE)"> Kane Hall (Southeast Entrance) </option>
                    <option value="KNE (S)"> Kane Hall (South Entrance) </option>
                    <option value="KNE (SW)"> Kane Hall (Southwest Entrance) </option>
                    <option value="LOW"> Loew Hall </option>
                    <option value="MGH"> Mary Gates Hall (North Entrance) </option>
                    <option value="MGH (E)"> Mary Gates Hall (East Entrance) </option>
                    <option value="MGH (S)"> Mary Gates Hall (South Entrance) </option>
                    <option value="MGH (SW)"> Mary Gates Hall (Southwest Entrance) </option>
                    <option value="MLR"> Miller Hall </option>
                    <option value="MOR"> Moore Hall </option>
                    <option value="MUS"> Music Building (Northwest Entrance) </option>
                    <option value="MUS (E)"> Music Building (East Entrance) </option>
                    <option value="MUS (SW)"> Music Building (Southwest Entrance) </option>
                    <option value="MUS (S)"> Music Building (South Entrance) </option>
                    <option value="OUG"> Odegaard Undergraduate Library </option>
                    <option value="PAA"> Physics/Astronomy Building A </option>
                    <option value="PAB"> Physics/Astronomy Building </option>
                    <option value="SAV"> Savery Hall </option>
                    <option value="SUZ"> Suzzallo Library </option>
                    <option value="T65"> Thai 65 </option>
                    <option value="FSH"> Fishery Sciences Building </option>
                    <option value="MCC"> McCarty Hall (Main Entrance) </option>
                    <option value="MCC (S)"> McCarty Hall (South Entrance) </option>
                    <option value="UBS"> University Bookstore </option>
                    <option value="UBS (Secret)"> University Bookstore (Secret Entrance) </option>
                    <option value="RAI"> Raitt Hall (West Entrance) </option>
                    <option value="RAI (E)"> Raitt Hall (East Entrance) </option>
                    <option value="ROB"> Roberts Hall </option>
                    <option value="CHL"> Chemistry Library (West Entrance) </option>
                    <option value="CHL (NE)"> Chemistry Library (Northeast Entrance) </option>
                    <option value="CHL (SE)"> Chemistry Library (Southeast Entrance) </option>
                    <option value="IMA"> Intramural Activities Building </option>
                    <option value="HUB"> Student Union Building (Main Entrance) </option>
                    <option value="HUB (West Food)"> Student Union Building (West Food Entrance) </option>
                    <option value="HUB (South Food)"> Student Union Building (South Food Entrance) </option>
                    <option value="MNY"> Meany Hall (Northeast Entrance) </option>
                    <option value="MNY (NW)"> Meany Hall (Northwest Entrance) </option>
                    <option value="PAR"> Parrington Hall </option>
                    <option value="MCM"> McMahon Hall (Northwest Entrance) </option>
                    <option value="MCM (SW)"> McMahon Hall (Southwest Entrance) </option>
                    <option value="CMU"> Communications Building </option>
                </select>

                <select value={this.state.end} onChange={this.setEndChange}>
                    <option value=""> </option>
                    <option value="BAG"> Bagley Hall (East Entrance) </option>
                    <option value="BAG (NE)"> Bagley Hall (Northeast Entrance) </option>
                    <option value="BGR"> By George </option>
                    <option value="CSE"> Paul G. Allen Center for Computer Science & Engineering </option>
                    <option value="CS2"> Bill & Melinda Gates Center For Computer Science & Engineering </option>
                    <option value="DEN"> Denny Hall </option>
                    <option value="EEB"> Electrical Engineering Building (North Entrance) </option>
                    <option value="EEB (S)"> Electrical Engineering Building (South Entrance) </option>
                    <option value="GWN"> Gowen Hall </option>
                    <option value="KNE"> Kane Hall (North Entrance) </option>
                    <option value="KNE (E)"> Kane Hall (East Entrance) </option>
                    <option value="KNE (SE)"> Kane Hall (Southeast Entrance) </option>
                    <option value="KNE (S)"> Kane Hall (South Entrance) </option>
                    <option value="KNE (SW)"> Kane Hall (Southwest Entrance) </option>
                    <option value="LOW"> Loew Hall </option>
                    <option value="MGH"> Mary Gates Hall (North Entrance) </option>
                    <option value="MGH (E)"> Mary Gates Hall (East Entrance) </option>
                    <option value="MGH (S)"> Mary Gates Hall (South Entrance) </option>
                    <option value="MGH (SW)"> Mary Gates Hall (Southwest Entrance) </option>
                    <option value="MLR"> Miller Hall </option>
                    <option value="MOR"> Moore Hall </option>
                    <option value="MUS"> Music Building (Northwest Entrance) </option>
                    <option value="MUS (E)"> Music Building (East Entrance) </option>
                    <option value="MUS (SW)"> Music Building (Southwest Entrance) </option>
                    <option value="MUS (S)"> Music Building (South Entrance) </option>
                    <option value="OUG"> Odegaard Undergraduate Library </option>
                    <option value="PAA"> Physics/Astronomy Building A </option>
                    <option value="PAB"> Physics/Astronomy Building </option>
                    <option value="SAV"> Savery Hall </option>
                    <option value="SUZ"> Suzzallo Library </option>
                    <option value="T65"> Thai 65 </option>
                    <option value="FSH"> Fishery Sciences Building </option>
                    <option value="MCC"> McCarty Hall (Main Entrance) </option>
                    <option value="MCC (S)"> McCarty Hall (South Entrance) </option>
                    <option value="UBS"> University Bookstore </option>
                    <option value="UBS (Secret)"> University Bookstore (Secret Entrance) </option>
                    <option value="RAI"> Raitt Hall (West Entrance) </option>
                    <option value="RAI (E)"> Raitt Hall (East Entrance) </option>
                    <option value="ROB"> Roberts Hall </option>
                    <option value="CHL"> Chemistry Library (West Entrance) </option>
                    <option value="CHL (NE)"> Chemistry Library (Northeast Entrance) </option>
                    <option value="CHL (SE)"> Chemistry Library (Southeast Entrance) </option>
                    <option value="IMA"> Intramural Activities Building </option>
                    <option value="HUB"> Student Union Building (Main Entrance) </option>
                    <option value="HUB (West Food)"> Student Union Building (West Food Entrance) </option>
                    <option value="HUB (South Food)"> Student Union Building (South Food Entrance) </option>
                    <option value="MNY"> Meany Hall (Northeast Entrance) </option>
                    <option value="MNY (NW)"> Meany Hall (Northwest Entrance) </option>
                    <option value="PAR"> Parrington Hall </option>
                    <option value="MCM"> McMahon Hall (Northwest Entrance) </option>
                    <option value="MCM (SW)"> McMahon Hall (Southwest Entrance) </option>
                    <option value="CMU"> Communications Building </option>
                </select>
            </div>
        )
    }
}

export default App;

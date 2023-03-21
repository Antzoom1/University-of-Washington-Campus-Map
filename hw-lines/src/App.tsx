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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map1 from "./Map1";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {edges: Map<Number, string[]>} // contains the edge list contents

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            edges: new Map<Number, string[]>()
            // TODO: store edges in this state
        };
    }

    render() {
        return (
            <div>
                <h1 id="app-title">Line Mapper!</h1>
                <div>
                    <Map1 edges={this.state.edges}/> // defines the props in the Map and passees them through
                </div>
                <EdgeList
                    onChange={(value) => {
                        this.setState({edges: value}); // uses the onChange function to store the
                        // edges in the specific state
                    }}
                />
            </div>
        );
    }
}

export default App;

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

interface EdgeListProps {
    onChange(edges: Map<Number, string[]>): void;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

interface EdgeListState {
    inputText: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            inputText: ""
        };
    }

    onTextChange = (event: any) => {
        this.setState(  {
            inputText: event.target.value //sets up the empty text change box
        });
    }

    onClearClick = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
         this.props.onChange(new Map()); //clears the map if the clear button is clicked
    }

    onDrawClick = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        let edgesInMap = this.state.inputText;
        let i = 1; //iterator
        let edgeMap = new Map();
        for(let element of edgesInMap.split( /\r?\n/)) { // this for loops splits the user input
            // 5 different pieces of data and checks if they fit the specifications
            let list = element.split( " ");
            if(list.length != 5 || isNaN(+list[0]) || isNaN(+list[1]) || isNaN(+list[2]) || isNaN(+list[3])
                || list.includes("")|| typeof list[4] !== 'string') {
                edgeMap.clear();
                window.alert("Enter Edge Data in the correct format: x1 y1 x2 y2 COLOR \n Also make sure to " +
                    "remove any white space");
                break;
            } else if(+list[0] < 0 || +list[0] > 4000 || +list[1] < 0 || +list[1] > 4000 || +list[2] < 0 ||
            +list[2] > 4000 ||+list[3] < 0 || +list[3] > 4000) {
                edgeMap.clear();
                window.alert("All Coordinates must be within (0,0) and (4000, 4000)");
                break;
            } else {
                edgeMap.set(i, element.split(" "));
                i++;
            }
        }
        if (edgeMap.size != 0) {
            this.props.onChange(edgeMap);
        }
    }



    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    value={this.state.inputText}
                    onChange={(event ) => {this.onTextChange(event)}}
                    rows={5}
                    cols={30}
                /> <br/>
                <button id= "draw" onClick={this.onDrawClick}>Draw</button>
                <button id= "clear" onClick={this.onClearClick}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;

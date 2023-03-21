package marvel.scriptTestRunner;

import graph.Graph;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

import static marvel.MarvelParser.parseData;
import static marvel.MarvelPaths.buildGraph;
import static marvel.MarvelPaths.findPath;

public class MarvelTestDriver {

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    // TODO for the student: Uncomment and parameterize the next line correctly:
    private final Map<String, Graph<String, String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "LoadGraph":
                    buildGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // TODO Insert your code here.

        graphs.put(graphName, new Graph<String, String>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // TODO Insert your code here.

        Graph<String, String> graph1 = graphs.get(graphName);
        graph1.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        // TODO Insert your code here.

        Graph<String, String> graph1 = graphs.get(graphName);
        graph1.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // TODO Insert your code here.

        Graph<String, String> graph1 = graphs.get(graphName);
        List<String> listNodes = graph1.getNodes();
        output.print(graphName + " contains:");
        for(String str: listNodes) {
            output.print(" " + str);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // TODO Insert your code here.

        Graph<String, String> graph1 = graphs.get(graphName);
        List<String> listChildren = graph1.childrenOfNode(parentName);
        Collections.sort(listChildren);
        String outputS = "the children of " + parentName + " in " + graphName + " are:";
        for(String str: listChildren ) {
            outputS += " " + str;

        }
        output.println(outputS);
    }

    private void buildGraph(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String fileName = arguments.get(1);
        buildGraph(graphName, fileName);
    }

    private void buildGraph(String graphName, String fileName) {
        graphs.put(graphName, MarvelPaths.buildGraph(fileName));
        output.println("loaded graph " + graphName);
    }


    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(2);
        String startPoint = arguments.get(0);
        String endPoint = arguments.get(1);

        findPath(startPoint, endPoint, graphName);
    }

    private void findPath(String graphName, String src, String dest) {
        Graph<String, String> newGraph = graphs.get(graphName);
        String startPoint = src.replaceAll("_", " ");
        String endPoint = dest.replaceAll("_", " ");
        try{
            if(!newGraph.getNodes().contains(startPoint) || !newGraph.getNodes().contains(endPoint)) {
                if(!newGraph.getNodes().contains(startPoint)) {
                    output.println("unknown: " + startPoint);
                }
                if(!newGraph.getNodes().contains(endPoint)){
                    output.println("unknown: " + endPoint);
                }
            } else{
                List<List<String>> path = MarvelPaths.findPath(startPoint,endPoint,newGraph);
                output.println("path from " + startPoint + " to " + endPoint + ":");
                for(List<String> edge: path) {
                    output.println(edge.get(0) + " to " + edge.get(2) + " via " + edge.get(1));
                }
            }
        }catch(NullPointerException e){
            output.println("no path found");
        }
    }


    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

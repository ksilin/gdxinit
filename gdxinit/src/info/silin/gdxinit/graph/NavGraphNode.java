package info.silin.gdxinit.graph;


public class NavGraphNode<T> extends GraphNode {

	
	private T[] coordinate;
	
	public NavGraphNode(int nodeIndex) {
		super(nodeIndex);
	}
	
	public T[] getCoordinates() {
		return coordinate;
	}
	
	public void setCoordinates(T[] value) {
		 this.coordinate =  value;
	}

	
}

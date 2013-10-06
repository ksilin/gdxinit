package info.silin.gdxinit.graph;

public class NavGraphNode<T> extends GraphNode {

	private T value;

	public NavGraphNode(int nodeIndex) {
		super(nodeIndex);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}

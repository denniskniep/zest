package org.mozilla.zest.core.v1;

import java.util.LinkedList;
import java.util.List;

public abstract class ZestStructuredExpression extends ZestExpression {
	private List<ZestExpressionElement> children = new LinkedList<>();

	public ZestStructuredExpression() {
		super();
	}

	public ZestStructuredExpression(
			List<ZestExpressionElement> childrenExpression) {
		super();
		this.children = childrenExpression;
	}

	/**
	 * sets the list of Children and return the previous list
	 * 
	 * @param new_list
	 *            the new list of Children Condition
	 * @return the previous list of Children Condition
	 */
	public List<ZestExpressionElement> setChildrenCondition(
			List<ZestExpressionElement> new_list) {
		List<ZestExpressionElement> old_children = children;
		this.children = new_list;
		return old_children;
	}

	/**
	 * returns the children condition of this Zest Expression
	 */
	public List<ZestExpressionElement> getChildrenCondition() {
		return this.children;
	}

	public boolean isLeaf() {
		return false;
	}

	/**
	 * adds a new Condition to the children (last position)
	 * 
	 * @param child
	 *            the child condition to add
	 */
	public void addChildCondition(ZestExpressionElement child) {
		children.add(child);
	}

	/**
	 * adds a new Condition to the children (give position)
	 * 
	 * @param child
	 *            the child condition to add
	 * @param position
	 *            the position where to add the condition
	 */
	public void addChildCondition(ZestExpressionElement child, int position) {
		this.children.add(position, child);
	}

	/**
	 * returns the Condition Child in position i
	 * 
	 * @param index
	 *            position of the child we're searching for
	 * @return the child found at the given position
	 */
	public ZestExpressionElement getChild(int index) {
		return children.get(index);
	}

}

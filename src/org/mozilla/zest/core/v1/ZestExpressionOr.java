package org.mozilla.zest.core.v1;

import java.util.List;

public class ZestExpressionOr extends ZestStructuredExpression implements
		ZestExpressionElement {
	/**
	 * Main construptor
	 * @param parent the parent of this ZestConditionalElement
	 */
	public ZestExpressionOr() {
		super();
	}
	/**
	 * Construptor
	 * @param parent the parent of this Conditional Element
	 * @param children the list of the OR clauses
	 */
	public ZestExpressionOr(List<ZestExpressionElement> children){
		super(children);
	}

	@Override
	public boolean evaluate(ZestResponse response) {
		boolean toReturn = false;
		for (ZestExpressionElement con : getChildrenCondition()) {
			toReturn = toReturn || con.evaluate(response);// compute OR for each child
			if(toReturn) break;//lazy evaluation
		}
		return isInverse() ? (!toReturn) : toReturn;
	}
	@Override
	public ZestExpression deepCopy() {
		ZestExpressionOr copy=new ZestExpressionOr();
		for(ZestExpressionElement child:this.getChildrenCondition()){
			copy.addChildCondition((ZestExpression)child.deepCopy());
		}
		return null;
	}
}

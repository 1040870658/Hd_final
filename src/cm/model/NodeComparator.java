package cm.model;


import java.util.Comparator;

public class NodeComparator implements Comparator<Node>
{
	@Override
	public int compare(cm.model.Node arg0, cm.model.Node arg1) {
		// TODO Auto-generated method stub
		if(arg0.num>arg1.num)
			return -1;
		else if(arg0.num==arg1.num)
			return 0;
		else
			return 1;
	}

}

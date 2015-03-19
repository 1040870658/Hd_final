package cm.model;
import java.util.Comparator;


public class ProStructComparator implements Comparator<ProStruct>
{

	@Override
	public int compare(ProStruct arg0, ProStruct arg1) {
		// TODO Auto-generated method stub
		return arg0.protime.compareTo(arg1.protime);
	}

}

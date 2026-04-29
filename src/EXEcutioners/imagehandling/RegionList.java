package EXEcutioners.imagehandling;

import java.util.ArrayList;
import java.util.Iterator;

public class RegionList implements Iterable<Region> {
	ArrayList<Region> regionList;
	int size=0;
	
	public RegionList()
	{
		regionList=new ArrayList<>();
	}
	
	public void addRegion(Region newRegion)
	{
		regionList.add(newRegion);
		size++;
	}
	
	public Boolean isEmpty()
	{
		return size<0;
	}
	
	public Region getRegion(int startx,int starty)
	{
		Region retReg=null;
		for(Region r:regionList)
		{
			if((r.x==startx) && (r.y==starty))
			{
				retReg=r;
			}
		}
		return retReg;
	}
	
	

	@Override
	public Iterator iterator() {

		return regionList.iterator();
	}
	
}

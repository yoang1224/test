package tiles;

import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;

public class MenuViewPreparer implements ViewPreparer {
    
	   public void execute(TilesRequestContext tilesContext,
			    AttributeContext attrContext) {
		   List<MenuItem> userMenus = new ArrayList<MenuItem>();
		   userMenus.add(new MenuItem("�޴�1", "link1"));
		   userMenus.add(new MenuItem("�޴�2", "link2"));
		   userMenus.add(new MenuItem("�޴�3", "link3"));
		   tilesContext.getRequestScope().put("userMenus", userMenus);
		   
		   List<MenuItem>adminMenus = new ArrayList<MenuItem>();
		   adminMenus.add(new MenuItem("�����޴�1","link1"));
		   adminMenus.add(new MenuItem("�����޴�2","link2"));
		   adminMenus.add(new MenuItem("�����޴�3","link3"));
		   attrContext.putAttribute("adminMenus", new Attribute(adminMenus),true);
	   
		   
	   }
}

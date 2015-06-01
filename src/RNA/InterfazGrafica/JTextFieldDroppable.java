package RNA.InterfazGrafica;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;

public class JTextFieldDroppable extends JTextField implements  DropTargetListener {

  DropTarget dt;

  public JTextFieldDroppable(){
    dt = new DropTarget(this,this);
  }

  public void dragEnter(DropTargetDragEvent dtde) {
    //System.out.println("Drag Enter");
  }

  public void dragExit(DropTargetEvent dte) {
    //System.out.println("Drag Exit");
  }

  public void dragOver(DropTargetDragEvent dtde) {
    //System.out.println("Drag Over");
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
    //System.out.println("Drop Action Changed");
  }

  public void drop(DropTargetDropEvent dtde) {

    this.setText("");
    try {
      // Ok, get the dropped object and try to figure out what it is
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
	//System.out.println("Possible flavor: "+ flavors[i].getMimeType());
	// Check for file lists specifically
	if (flavors[i].isFlavorJavaFileListType()) {
	  // Great! Accept copy drops...
	  dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	  //ta.setText("Successful file list drop.\n\n");

	  // And add the list of file names to our text area
	  java.util.List list = (java.util.List) tr.getTransferData(flavors[i]);
	  for (int j = 0; j < list.size(); j++) {
	    this.setText(list.get(j) + "\n");
	  }

	  // If we made it this far, everything worked.
	  dtde.dropComplete(true);
	  return;
	}
	// Ok, is it another Java object?
	else if (flavors[i].isFlavorSerializedObjectType()) {
	  dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	  //ta.setText("Successful text drop.\n\n");
	  Object o = tr.getTransferData(flavors[i]);
	  this.setText("Object: " + o);
	  dtde.dropComplete(true);
	  return;
	}
	// How about an input stream?
	else if (flavors[i].isRepresentationClassInputStream()) {
	  dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	  //ta.setText("Successful text drop.\n\n");
//ta.read(new InputStreamReader( (InputStream) tr.getTransferData(flavors[i])), "from system clipboard");
	  dtde.dropComplete(true);
	  return;
	}
      }
      // Hmm, the user must not have dropped a file list
      System.out.println("Drop failed: " + dtde);
      dtde.rejectDrop();
    } catch (Exception e) {
      e.printStackTrace();
      dtde.rejectDrop();
    }
  }

}

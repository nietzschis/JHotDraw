package org.jhotdraw.draw;

import org.jhotdraw.graph.Graph;
import org.jhotdraw.util.ResourceBundleUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Map;
import org.jhotdraw.gui.AbstractFunctionPanel;


public class PredefinedFunctionTool extends AbstractTool {

    /**
     * Attributes to be applied to the created ConnectionFigure.
     * These attributes override the default attributes of the
     * DrawingEditor.
     */
    protected Map<AttributeKey, Object> prototypeAttributes;
    /**
     * A localized name for this tool. The presentationName is displayed by the
     * UndoableEdit.
     */
    protected String presentationName;

    /**
     * The prototype for new figures.
     */
    protected Figure prototype;
    /**
     * The created figure.
     */
    protected PredefinedBezierFigure createdFigure;
    /**
     * If this is set to false, the PredefinedFunctionTool does not fire toolDone
     * after a new Figure has been created. This allows to create multiple
     * figures consecutively.
     */
    private boolean isToolDoneAfterCreation = true;

    private AbstractFunctionPanel[] jPanel;
    
    private Graph graph;

    /**
     * Creates a new instance with the specified prototype and attribute set.
     *
     * @param prototype The prototype used to create a new Figure.
     * @param attributes The PredefinedFunctionTool applies these attributes to the
     * prototype after having applied the default attributes from the DrawingEditor.
     */
    public PredefinedFunctionTool(Figure prototype, Map<AttributeKey, Object> attributes) {
        this.prototype = prototype; 
        this.prototypeAttributes = attributes;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        this.presentationName = labels.getString("edit.createGraph.text");
    }

    private PredefinedFunction createFunction() {
        boolean notdone = true;
        AbstractFunctionPanel panel = null;
        
        JDialog f = null;
        while (notdone) {
            f = new JDialog();
            if (panel == null) {
                panel = jPanel[0];
            } else {
                panel = panel.changeFrame();
            }
            panel.setDialog(f);
            f.setUndecorated(true);
            f.setTitle("");
            f.setType(javax.swing.JFrame.Type.UTILITY);
            f.setModal(true);
            f.setSize(400, 300);
            f.setLocationRelativeTo(null);
            f.add(panel);
            f.setBackground(Color.BLACK);
            f.pack();
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            f.setVisible(true);
            notdone = panel.isDone();
        }
        return panel.getGraph();
    }

    @Override
    public void activate(DrawingEditor editor) {
        super.activate(editor);
        graph = (Graph) createFunction();
        if (graph == null) {
            fireToolDone();
        }
        getView().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void deactivate(DrawingEditor editor) {
        super.deactivate(editor);
        if (getView() != null) {
            getView().setCursor(Cursor.getDefaultCursor());
        }
        if (createdFigure != null) {
            if (createdFigure instanceof CompositeFigure) {
                ((CompositeFigure) createdFigure).layout();
            }
            createdFigure = null;
        }
    }

    public void mousePressed(MouseEvent evt) { //Called
        super.mousePressed(evt);
        getView().clearSelection();
        createdFigure = createFigure();
        createdFigure.setFunction(graph);
        Point2D.Double p = constrainPoint(viewToDrawing(anchor));
        anchor.x = evt.getX();
        anchor.y = evt.getY();
        createdFigure.setBounds(p, p);
        getDrawing().add(createdFigure);
        creationFinished(createdFigure);
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        
    }

    @SuppressWarnings("unchecked")
    protected PredefinedBezierFigure createFigure() {
        PredefinedBezierFigure f = (PredefinedBezierFigure) prototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if (prototypeAttributes != null) {
            for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet()) {
                entry.getKey().basicSet(f, entry.getValue());
            }
        }
        return f;
    }

    protected Figure getCreatedFigure() {
        return createdFigure;
    }

    protected Figure getAddedFigure() {
        return createdFigure;
    }

    /**
     * This method allows subclasses to do perform additonal user interactions
     * after the new figure has been created.
     * The implementation of this class just invokes fireToolDone.
     */
    protected void creationFinished(Figure createdFigure) {
        if (createdFigure.isSelectable()) {
            getView().addToSelection(createdFigure);
        }
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }
    
    /**
     * Sets the lists of jpanels to be used in the dialog boxes.
     * The Jpanels has to an AbstractFunctionPanel.
     * @param jPanel 
     */
    public void setList(AbstractFunctionPanel[] jPanel) {
        for (AbstractFunctionPanel abstractFunctionPanel : jPanel) {
            abstractFunctionPanel.setList(jPanel);
        }
        this.jPanel = jPanel;
    }

    /**
     * If this is set to false, the PredefinedFunctionTool does not fire toolDone
     * after a new Figure has been created. This allows to create multiple
     * figures consecutively.
     */
    public void setToolDoneAfterCreation(boolean newValue) {
        isToolDoneAfterCreation = newValue;
    }

    /**
     * Returns true, if this tool fires toolDone immediately after a new
     * figure has been created.
     */
    public boolean isToolDoneAfterCreation() {
        return isToolDoneAfterCreation;
    }
}

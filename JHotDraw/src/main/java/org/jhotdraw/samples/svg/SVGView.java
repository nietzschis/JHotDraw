/*
 * @(#)SVGView.java  2.0  2009-04-10
 *
 * Copyright (c) 1996-2009 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 *
 */
package org.jhotdraw.samples.svg;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.image.BufferedImage;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.samples.svg.io.*;
import org.jhotdraw.undo.*;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.app.*;
import org.jhotdraw.app.action.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.tabs.gui.TabListener;
import org.jhotdraw.tabs.gui.TabPanel;

/**
 * A view for SVG drawings.
 *
 * @author Werner Randelshofer
 * @version 2.0 2009-04-10 Moved all drawing related toolbars into
 * SVGDrawingPanel.
 * <br>1.3.1 2008-03-19 Method read() tries out now all supported files format.
 * <br>1.3 2007-11-25 Method clear is now invoked on a worker thread.
 * <br>1.2 2006-12-10 Used SVGStorage for reading SVG drawing (experimental).
 * <br>1.1 2006-06-10 Extended to support DefaultDrawApplicationModel.
 * <br>1.0 2006-02-07 Created.
 */
public class SVGView extends AbstractView implements ExportableView {

    public final static String GRID_VISIBLE_PROPERTY = "gridVisible";

    protected JFileChooser exportChooser;
    protected BufferedImage watermarkImage = null;
    protected TabPanel tabs;
    
    //To fix issue with startup
    private boolean firstTab = true;

    public TabPanel getTabs()
    {
        return tabs;
    }
    /**
     * Each SVGView uses its own undo redo manager. This allows for undoing and
     * redoing actions per view.
     */
    private UndoRedoManager undo;
    HashMap<Drawing, Collection<UndoableEdit>> undoHistoryForTabs = new HashMap<>();

    private HashMap<javax.swing.filechooser.FileFilter, InputFormat> fileFilterInputFormatMap;
    private HashMap<javax.swing.filechooser.FileFilter, OutputFormat> fileFilterOutputFormatMap;

    /**
     * Creates a new View.
     */
    public SVGView() {
    }

    /**
     * Initializes the View.
     */
    @Override
    public void init() {
        super.init();

        undo = new UndoRedoManager();
        initComponents();
        svgPanel.setUndoManager(undo);
        
        JPanel zoomButtonPanel = new JPanel(new BorderLayout());
        
        
        
        tabs = new TabPanel();
        tabs.setSelectedTabChanged( new TabListener()
        {
            @Override
            public void ChangeTab()
            {
                if(tabs.tabExist())
                    changeDrawing(tabs.getCurrentDrawing());
                else
                    newDrawing(createDrawing(), "untitled");
            }
            @Override
            public void CloseTab()
            {
                new CloseTabAction(getApplication()).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "tabClosing"));
            }
        });
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setVgap(0);
        layout.setHgap(0);
        tabPanel.setLayout(layout);
        tabPanel.add(tabs);
        
        Drawing d = createDrawing();
        tabs.addTab(d, "untitled");
        
        changeDrawing(tabs.getCurrentDrawing());
        
        getDrawing().addUndoableEditListener(undo);
        
        initActions();
        undo.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                setHasUnsavedChanges(undo.hasSignificantEdits());
            }
        });
    }

    /**
     * Creates a new Drawing for this View.
     */
    protected Drawing createDrawing() {
        Drawing drawing = new QuadTreeDrawing();

        drawing.setInputFormats(inputFormats());     
        drawing.setOutputFormats(outputFormats());

        return drawing;
    }
    
    protected LinkedList<InputFormat> inputFormats(){
                LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        inputFormats.add(new SVGZInputFormat());
        inputFormats.add(new ImageInputFormat(new SVGImageFigure()));
        inputFormats.add(new ImageInputFormat(new SVGImageFigure(), "JPG", "Joint Photographics Experts Group (JPEG)", "jpg", BufferedImage.TYPE_INT_RGB));
        inputFormats.add(new ImageInputFormat(new SVGImageFigure(), "GIF", "Graphics Interchange Format (GIF)", "gif", BufferedImage.TYPE_INT_ARGB));
        inputFormats.add(new ImageInputFormat(new SVGImageFigure(), "PNG", "Portable Network Graphics (PNG)", "png", BufferedImage.TYPE_INT_ARGB));
        inputFormats.add(new PictImageInputFormat(new SVGImageFigure()));
        inputFormats.add(new TextInputFormat(new SVGTextFigure()));
        
        return inputFormats;
    }
    
    protected LinkedList<OutputFormat> outputFormats(){
        LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
        outputFormats.add(new SVGOutputFormat());
        outputFormats.add(new SVGZOutputFormat());
        outputFormats.add(new ImageOutputFormat());
        outputFormats.add(new ImageOutputFormat("JPG", "Joint Photographics Experts Group (JPEG)", "jpg", BufferedImage.TYPE_INT_RGB));
        outputFormats.add(new ImageOutputFormat("BMP", "Windows Bitmap (BMP)", "bmp", BufferedImage.TYPE_BYTE_INDEXED));
        outputFormats.add(new ImageOutputFormat("PNG", "Compressed Portable Network Graphics (PNG)", "png", BufferedImage.TYPE_INT_ARGB));
        outputFormats.add(new ImageMapOutputFormat());
        
        return outputFormats;
    }

    /**
     * Creates a Pageable object for printing the View.
     */
    public Pageable createPageable() {
        return new DrawingPageable(getDrawing());

    }

    public DrawingEditor getEditor() {
        return svgPanel.getEditor();
    }

    public void setEditor(DrawingEditor newValue) {
        svgPanel.setEditor(newValue);
    }

    /**
     * Initializes view specific actions.
     */
    private void initActions() {
        putAction(UndoAction.ID, undo.getUndoAction());
        putAction(RedoAction.ID, undo.getRedoAction());
    }

    protected void setHasUnsavedChanges(boolean newValue) {
        super.setHasUnsavedChanges(newValue);
        undo.setHasSignificantEdits(newValue);
    }

    /**
     * Writes the view to the specified file.
     */
    @FeatureEntryPoint(JHotDrawFeatures.DRAWING_PERSITENCE)
    public void write(File f) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(f));
            new SVGOutputFormat().write(f, getDrawing());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        
        tabs.setTabName(f.getName());
    }

    /**
     * Reads the view from the specified file.
     */
    @FeatureEntryPoint(JHotDrawFeatures.DRAWING_PERSITENCE)
    public void read(File f) throws IOException {
        try {
            JFileChooser fc = getOpenChooser();

            final Drawing drawing = createDrawing();

            // We start with the selected file format in the file chooser,
            // and then try out all formats we can import.
            // We need to try out all formats, because the user may have
            // chosen to load a file without having used the file chooser.
            InputFormat selectedFormat = fileFilterInputFormatMap.get(fc.getFileFilter());
            boolean success = false;
            if (selectedFormat != null) {
                try {
                    selectedFormat.read(f, drawing, true);
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    // try with the next input format
                }
            }
            if (!success) {
                for (InputFormat sfi : drawing.getInputFormats()) {
                    if (sfi != selectedFormat) {
                        try {
                            sfi.read(f, drawing, true);
                            success = true;
                            break;
                        } catch (Exception e) {
                            // try with the next input format
                        }
                    }
                }
            }
            if (!success) {
                ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
                throw new IOException(labels.getFormatted("file.open.unsupportedFileFormat.message", f.getName()));
            }
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    newDrawing(drawing, f.getName());
                }
            });
        } catch (InterruptedException e) {
            InternalError error = new InternalError();
            e.initCause(e);
            throw error;
        } catch (InvocationTargetException e) {
            InternalError error = new InternalError();
            error.initCause(e);
            throw error;
        }
    }

    public Drawing getDrawing() {
        return tabs.getCurrentDrawing();
    }

    public void setEnabled(boolean newValue) {
        svgPanel.setEnabled(newValue);
        super.setEnabled(newValue);
    }
    
    
    

    
    
     private void changeDrawing(Drawing d)
    {
        
        
        if(svgPanel.getDrawing() != null)
        {
            svgPanel.getDrawing().removeUndoableEditListener(undo);
            undoHistoryForTabs.put(svgPanel.getDrawing(), undo.getEdits());
        }        
        
        svgPanel.setDrawing(d);
        
        
        if(undoHistoryForTabs.containsKey(d))
            undo.setEdits(undoHistoryForTabs.get(d));
        else
            undo.setEdits(new ArrayList<>());
        
        getDrawing().addUndoableEditListener(undo);
        
    }
    
    private void newDrawing(Drawing d, String title)
    {
        tabs.addTab(d, title);
        changeDrawing(d);
        
    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {

        
        final Drawing newDrawing = createDrawing();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    //The first Tab is broken due to being created too early
                    //as a Drawing is required pre startup, could use a proper
                    //fix
                    if(firstTab)
                    {
                        tabs.CloseTab();
                        firstTab = false;
                    }
                    else
                        newDrawing(newDrawing, "untitled");

                }
            });
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        
    }

    @Override
    protected JFileChooser createOpenChooser() {
        final JFileChooser c = new JFileChooser();
        fileFilterInputFormatMap = new HashMap<javax.swing.filechooser.FileFilter, InputFormat>();
        javax.swing.filechooser.FileFilter firstFF = null;
        for (InputFormat format : getDrawing().getInputFormats()) {
            javax.swing.filechooser.FileFilter ff = format.getFileFilter();
            if (firstFF == null) {
                firstFF = ff;
            }
            fileFilterInputFormatMap.put(ff, format);
            c.addChoosableFileFilter(ff);
        }
        c.setFileFilter(firstFF);
        c.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("fileFilterChanged")) {
                    InputFormat inputFormat = fileFilterInputFormatMap.get(evt.getNewValue());
                    c.setAccessory((inputFormat == null) ? null : inputFormat.getInputFormatAccessory());
                }
            }
        });
        if (preferences != null) {
            c.setSelectedFile(new File(preferences.get("projectFile", System.getProperty("user.home"))));
        }

        return c;
    }

    @Override
    protected JFileChooser createSaveChooser() {
        JFileChooser c = new JFileChooser();

        fileFilterOutputFormatMap = new HashMap<javax.swing.filechooser.FileFilter, OutputFormat>();
        //  c.addChoosableFileFilter(new ExtensionFileFilter("SVG Drawing","svg"));
        for (OutputFormat format : getDrawing().getOutputFormats()) {
            javax.swing.filechooser.FileFilter ff = format.getFileFilter();
            fileFilterOutputFormatMap.put(ff, format);
            c.addChoosableFileFilter(ff);
            break; // only add the first file filter
        }
        if (preferences != null) {
            c.setSelectedFile(new File(preferences.get("projectFile", System.getProperty("user.home"))));
        }

        return c;
    }

    protected JFileChooser createExportChooser() {
        JFileChooser c = new JFileChooser();

        fileFilterOutputFormatMap = new HashMap<javax.swing.filechooser.FileFilter, OutputFormat>();
        //  c.addChoosableFileFilter(new ExtensionFileFilter("SVG Drawing","svg"));
        javax.swing.filechooser.FileFilter currentFilter = null;
        for (OutputFormat format : getDrawing().getOutputFormats()) {
            javax.swing.filechooser.FileFilter ff = format.getFileFilter();
            fileFilterOutputFormatMap.put(ff, format);
            c.addChoosableFileFilter(ff);
            if (ff.getDescription().equals(preferences.get("viewExportFormat", ""))) {
                currentFilter = ff;
            }
        }
        if (currentFilter != null) {
            c.setFileFilter(currentFilter);
        }
        c.setSelectedFile(new File(preferences.get("viewExportFile", System.getProperty("user.home"))));

        return c;
    }

    @Override
    public boolean canSaveTo(File file) {
        return file.getName().endsWith(".svg")
                || file.getName().endsWith(".svgz");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        svgPanel = new org.jhotdraw.samples.svg.SVGDrawingPanel();
        tabPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());
        add(svgPanel, java.awt.BorderLayout.CENTER);
        add(tabPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents
    public JFileChooser getExportChooser() {
        if (exportChooser == null) {
            exportChooser = createExportChooser();
        }
        return exportChooser;
    }

    @FeatureEntryPoint(JHotDrawFeatures.EXPORT)
    public void export(File f, javax.swing.filechooser.FileFilter filter, Component accessory) throws IOException {
        OutputFormat format = fileFilterOutputFormatMap.get(filter);

        if (!f.getName().endsWith("." + format.getFileExtension())) {
            f = new File(f.getPath() + "." + format.getFileExtension());
        }

        format.write(f, svgPanel.getDrawing());

        
        format.write(f, getDrawing());
        
        // If selected format was "compressed PNG", compress an image with a web service tinypng.
        if (filter.getDescription().equals("Compressed Portable Network Graphics (PNG)")) {
            TinyPngCompressAction.compressImage(f);
        }

        preferences.put("viewExportFile", f.getPath());
        preferences.put("viewExportFormat", filter.getDescription());
        
        if (watermarkImage != null && (format.getFileExtension().equals("png") || format.getFileExtension().equals("jpg") || format.getFileExtension().equals("gif"))) {
            try {
                addImageWatermark(f, format, watermarkImage, new File(f.getPath().substring(0, f.getPath().lastIndexOf('.')) + "_watermarked." + format.getFileExtension()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    @Override
    public void addImageWatermark(File sourceFile, OutputFormat format, BufferedImage watermarkImage, final File finalFile) throws IOException {

        BufferedImage viewImage = ImageIO.read(sourceFile);

        Graphics2D g2d = (Graphics2D) viewImage.getGraphics();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alpha);

        g2d.drawImage(watermarkImage, 0, 0, viewImage.getWidth(), viewImage.getHeight(), null);

        ImageIO.write(viewImage, format.getFileExtension(), finalFile);
        g2d.dispose();
    }

    @FeatureEntryPoint(JHotDrawFeatures.IMPORT_WATERMARK)
    public void setWatermark(BufferedImage watermark) {
        this.watermarkImage = watermark;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jhotdraw.samples.svg.SVGDrawingPanel svgPanel;
    private javax.swing.JPanel tabPanel;
    // End of variables declaration//GEN-END:variables
}

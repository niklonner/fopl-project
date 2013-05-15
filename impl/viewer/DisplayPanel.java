package viewer;

import java.util.List;
import generic.SubTournament;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;

import org.apache.batik.swing.*;
import org.apache.batik.svggen.*;
import org.apache.batik.dom.svg.SVGDOMImplementation;

import org.w3c.dom.*;
import org.w3c.dom.svg.*;

public class DisplayPanel extends JPanel {
    JSVGCanvas canvas;
    DisplayListener dl;

    public DisplayPanel() {
        super(new BorderLayout());
        setBackground(Color.decode("#202020"));
        canvas = makeCanvas();

        add("Center", canvas);
    }

    public void loadSvg(String path) throws IOException {
        canvas.setURI(path);
    }

    public void setDisplayListener(DisplayListener dl) {
        this.dl = dl;
    }

    public void drawTournament(List<SubTournament<?>> subts) {
        // Create an SVG document.
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        SVGDocument doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);

        // Create a converter for this document.
        SVGGraphics2D g = new SVGGraphics2D(doc);

        //Draw tournament
        draw(g, subts);

        // Populate the document root with the generated SVG content.
        Element root = doc.getDocumentElement();
        g.getRoot(root);

        // Display the document.
        canvas = makeCanvas();

        BorderLayout layout = (BorderLayout) getLayout();
        remove(layout.getLayoutComponent(BorderLayout.CENTER));
        add("Center", canvas);
        validate();

        canvas.setSVGDocument(doc);
        canvas.setVisible(true);
        dl.onBuildComplete();
    }

    private void draw(SVGGraphics2D g, List<SubTournament<?>> subts) {
        Shape box = new Rectangle2D.Double(0,0,50,50);
        for(SubTournament subt : subts) {
            g.setPaint(Color.white);
            g.fill(box);
            g.translate(60, 0);
        }

        //Shape circle = new Ellipse2D.Double(0, 0, 50, 50);
        //g.setPaint(Color.red);
        //g.fill(circle);
        //g.translate(60, 0);
        //g.setPaint(Color.green);
        //g.fill(circle);
        //g.translate(60, 0);
        //g.setPaint(Color.blue);
        //g.fill(circle);
    }

    private JSVGCanvas makeCanvas() {
        canvas = new JSVGCanvas();
        canvas.setBackground(Color.decode("#202020"));

        canvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
            public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
                System.out.println("Document Loading...");
            }
            public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
                System.out.println("Document Loaded.");
            }
        });

        canvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildStarted(GVTTreeBuilderEvent e) {
                System.out.println("Build Started...");
            }
            public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
                System.out.println("Build Done.");
                dl.onBuildComplete();
            }
        });

        canvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
                System.out.println("Rendering Started...");
            }
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                System.out.println("Rendering Completed");
            }
        });

        return canvas;
    }

    public interface DisplayListener {
        public void onBuildComplete();
    }
}

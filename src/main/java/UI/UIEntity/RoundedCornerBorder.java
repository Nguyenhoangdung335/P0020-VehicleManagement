package UI.UIEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

public class RoundedCornerBorder extends AbstractBorder{
    private static final Color ALPHA_ZERO = new Color(0x0, true);
    public static final int ARCSIZE = 15;
    public static final int BORDERSIZE = 1;
    public static final Color BORDERCOLOR = Color.GRAY;
    
    private int arcSize = ARCSIZE;
    private int borderSize = BORDERSIZE;
    private Color borderColor = BORDERCOLOR;
    
    int borderSizeOffset = Math.floorDiv(borderSize, 2);
    private Shape borderShape;

    public RoundedCornerBorder() {
        super();
    }
    
    public RoundedCornerBorder(int arcSize) {
        super();
        this.arcSize = arcSize;
    }
    
    public RoundedCornerBorder(int arcSize, int borderSize) {
        super();
        this.arcSize = arcSize;
        this.borderSize = borderSize;
        this.borderSizeOffset = Math.floorDiv(borderSize, 2);
    }
    
    public RoundedCornerBorder(int arcSize, int borderSize, Color boderColor) {
        super();
        this.arcSize = arcSize;
        this.borderSize = borderSize;
        this.borderSizeOffset = Math.floorDiv(borderSize, 2);
        this.borderColor = borderColor;
    }
    
    public Shape getBorderShape () {
        return borderShape;
    }
    
    public void setBorderShape (Shape newShape) {
        this.borderShape = newShape;
    }
    
    public int getBorderOffset() {
        return borderSizeOffset;
    }
    
    public Color getBorderColor () {
        return borderColor;
    }
    
    public void setBorderColor (Color newColor) {
        this.borderColor = newColor;
    }
    
    @Override 
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
        x = x + borderSizeOffset;       y = y + borderSizeOffset;
        if (x % 2 == 0) x++;
        if (y % 2 == 0) y++;
        width = width - borderSize;     height = height - borderSize;
        if ((x + width) % 2 == 0) width--;
        if ((y + height) % 2 == 0) height--;
        
        //x += borderSize;        y += borderSize;
        
        Shape border = getBorderShape(x, y, width, height);
        borderShape = border;
        Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
        corner.subtract(new Area(border));
        
        g2.setPaint(ALPHA_ZERO);
        g2.fill(corner);
        g2.setStroke(new BasicStroke(borderSize));
        g2.setPaint(borderColor);
        g2.draw(border);
        g2.dispose();
    }
    public Shape getBorderShape(int x, int y, int w, int h) {
        int r = (arcSize < 0 || arcSize > h)? h: arcSize;
        return new RoundRectangle2D.Double(x, y, w, h, r, r);
    }
    
    @Override 
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }
      
    @Override 
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 8, 4, 8);
        return insets;
    }
}

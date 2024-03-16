package View.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * A parent class that makes using a GridBagLayout more efficient
 *
 * @author Dillon Crookshank
 * @version 1.0
 */
public class GridPanel extends JPanel {

    /** The grid bag constraints used when adding components to the panel. */
    protected final GridBagConstraints myGridBagCons = new GridBagConstraints();

    /**
     * The constructor.
     * @param theName The name given to the component.
     */
    public GridPanel(final String theName) {
        super(new GridBagLayout());
        this.setName(theName);
    }

    /**
     * Sets the 'gridx' and 'gridy' properties of myGridBagCons
     * @param theXCoordinate The new value of 'gridx'
     * @param theYCoordinate The new value of 'gridy'
     */
    protected final void setGBLocation(final int theXCoordinate, final int theYCoordinate) {
        myGridBagCons.gridx = theXCoordinate;
        myGridBagCons.gridy = theYCoordinate;
    }

    /**
     * Sets the 'gridwidth' and 'gridheight' properties of myGridBagCons
     * @param theWidth The new value of 'gridwidth'
     * @param theHeight The new value of 'gridheight'
     */
    protected final void setGBSize(final int theWidth, final int theHeight) {
        myGridBagCons.gridwidth = theWidth;
        myGridBagCons.gridheight = theHeight;
    }

    /**
     * Sets the 'weightx' and 'weighty' properties of myGridBagCons
     * @param theWeightX The new value of 'weightx'
     * @param theWeightY The new value of 'weighty'
     */
    protected final void setGBWeight(final double theWeightX, final double theWeightY) {
        myGridBagCons.weightx = theWeightX;
        myGridBagCons.weighty = theWeightY;
    }

    /**
     * Sets the 'fill' property of myGridBagCons
     * @param theFill The new value of 'fill'
     */
    protected final void setGBFill(final int theFill) {
        myGridBagCons.fill = theFill;
    }

    /**
     * Sets the 'anchor' property of myGridBagCons
     * @param theAnchor The new value of 'anchor'
     */
    protected final void setGBAnchor(final int theAnchor) {
        myGridBagCons.anchor = theAnchor;
    }

    /**
     * Sets the 'insets' property of myGridBagCons, uses the arguments to create a new Insets instance.
     * @param theTopInset The new Top Inset value
     * @param theLeftInset The new Left Inset value
     * @param theBottomInset The new Bottom Inset value
     * @param theRightInset The new Right Inset value
     */
    protected final void setGBInsets(final int theTopInset, final int theLeftInset, final int theBottomInset, final int theRightInset) {
        myGridBagCons.insets = new Insets(theTopInset, theLeftInset, theBottomInset, theRightInset);
    }

    /**
     * Sets the 'ipadx' and 'ipady' properties of myGridBagCons
     * @param thePadX The new value of 'ipadx'
     * @param thePadY The new value of 'ipady'
     */
    protected final void setGBPadding(final int thePadX, final int thePadY) {
        myGridBagCons.ipadx = thePadX;
        myGridBagCons.ipady = thePadY;
    }
}

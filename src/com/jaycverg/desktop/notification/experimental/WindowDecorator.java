package com.jaycverg.desktop.notification.experimental;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.SwingUtilities;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class WindowDecorator
{

    public static void decorateDraggable(Component component)
    {
        new DraggableComponentWrapper(component);
    }

    private static class DraggableComponentWrapper
    {

        private final Component component;
        private final Insets anchorInsets = new Insets(0, 0, 0, 0);

        // lazily initialized
        private Window _parent;

        DraggableComponentWrapper(Component component)
        {
            this.component = component;

            component.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    Point wLocation = getParent().getLocationOnScreen();
                    Point anchor = e.getLocationOnScreen();

                    anchorInsets.left = anchor.x - wLocation.x;
                    anchorInsets.top = anchor.y - wLocation.y;
                }
            });

            component.addMouseMotionListener(new MouseMotionAdapter()
            {
                @Override
                public void mouseDragged(MouseEvent e)
                {
                    getParent().setLocation(
                            e.getXOnScreen() - anchorInsets.left,
                            e.getYOnScreen() - anchorInsets.top);
                }
            });
        }

        private Window getParent()
        {
            return _parent == null
                    ? (_parent = SwingUtilities.getWindowAncestor(component))
                    : _parent;
        }
    }
}

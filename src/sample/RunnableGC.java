package sample;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by bearden-tellez on 9/4/16.
 */
public class RunnableGC implements Runnable {

    private GraphicsContext gc = null;
    private Stroke stroke = null;

    public RunnableGC(GraphicsContext gc, Stroke stroke) {
        this.gc = gc;
        this.stroke = stroke;
    }

    public void run() {
        gc.strokeOval(stroke.x, stroke.y, stroke.strokeS, stroke.strokeS); // <---- this is the actual work we need to do
    }
}

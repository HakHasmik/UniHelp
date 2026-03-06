package hasmik.hakobyan.unihelp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CurveDraw extends View {

    private Paint paint;
    private float middleOffset = 100f; // поднимаем среднюю точку
    private int curveColor = Color.parseColor("#A3D9A5"); // светло-зелёный

    public CurveDraw(Context context) {
        super(context);
        init();
    }

    public CurveDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(curveColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        Path path = new Path();
        path.moveTo(0, height); // левая нижняя точка
        path.lineTo(width * 0.4f, height - middleOffset); // средняя точка поднята
        path.lineTo(width, height); // правая нижняя точка
        path.lineTo(width, 0); // правая верхняя
        path.lineTo(0, 0); // левая верхняя
        path.close();

        canvas.drawPath(path, paint);
    }

    // Методы для динамического изменения
    public void setCurveColor(int color) {
        this.curveColor = color;
        paint.setColor(curveColor);
        invalidate();
    }

    public void setMiddleOffset(float offset) {
        this.middleOffset = offset;
        invalidate();
    }
}

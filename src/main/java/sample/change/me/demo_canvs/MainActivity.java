package sample.change.me.demo_canvs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 *  Compass 指南针
 */
public class MainActivity extends Activity {
    private static final String TAG="MainActivity";
    private Paint paint;
    private int begin=0;
    private SensorManager sensorManager;
    private float gravity[]=new float[3];
    private float linear_accelerometer[] =new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        CustomView customView=new CustomView(this);
        setContentView(customView);
        //获取传感器管理器
        sensorManager =(SensorManager)getSystemService(SENSOR_SERVICE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //获取重力传感器
        Sensor acceler =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, acceler, SensorManager.SENSOR_DELAY_GAME);
        //获取方向传感器
        @SuppressWarnings("deprecation")
        Sensor orient =sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(listener, orient, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //注销所有传感器
        sensorManager.unregisterListener(listener);
    }
    private SensorEventListener listener =new SensorEventListener() {
        boolean Switch = true;
        @SuppressWarnings("deprecation")
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    float alpha = 0.8f;
                    gravity[0] = alpha * gravity[0] + (1 - alpha) * x;
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * y;
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * z;

                    linear_accelerometer[0] = x - gravity[0];
                    linear_accelerometer[1] = y - gravity[1];
                    linear_accelerometer[2] = z - gravity[2];
                    //线性加速度
//				x=linear_accelerometer[0];
//				y=linear_accelerometer[1];
//				z=linear_accelerometer[2];
                    //重力加速度
      /*              x = gravity[0];
                    y = gravity[1];
                    z = gravity[2];

                    double xs =-0.50;
                    double xa =0.50;
                    double ys =-0.50;
                    double ya =0.50;
                    double zs =9.8;
                    double za =10.4;*/
//                    Log.d(TAG, "onSensorChanged: " +String.format("%.2f",x)+"......"+String.format("%.2f",y)+"....."+String.format("%.1f",z));
//                    Log.d(TAG, "onSensorChanged: " +Math.ceil((double)x)+"......"+Math.floor((double)y)+"....."+Math.floor((double)z));
           /*         if((x>=xs&&x<=xa)&&(y>=ys&&y<=ya)&&(z>=zs&&z<=za)){
//                        accelerometer.setText("水平状态");
                        Switch = true;
                    } else {
//                        accelerometer.setText("倾斜状态,请先置水平");
                        Switch = false;
//                        flag=0f;
//                        sum=0;
//                        on=true;
                    }*/


                    break;
                case Sensor.TYPE_ORIENTATION:
                    if (Switch) {
                        double X = Math.floor(x);
                        int range = 22;
                        int deg=180;
                        // 指向正北
                        Log.d(TAG, "onSensorChanged: X-----------------"+X);
                        if(X > 360 - range && X < 360 + range)
                        {
                            begin= (int) (X-170);
                        }
                        if(X > 330 - range && X < 350)
                        {
                            begin= (int) (X-150);
                        }
                        // 指向正东
                        if(X > 90 - range && X < 90 + range)
                        {
                            begin= (int) (deg-X);
                        }
                        // 指向正南
                        if(X > 180 - range && X < 180 + range)
                        {
                            begin= (int) (X-deg);
                        }
                        if(X > 190 && X < 207 )
                        {
                            begin= (int) (X-deg-20);
                        }
                        // 指向正西
                        if(X > 270 - range && X < 270 + range)
                        {
                            begin= (int) (deg-X);
                        }
                        // 东偏北
                        if(X > 0 && X < 45)
                        {
                            begin= (int) (deg-X);
                        }
                        // 指向东北
                        if(X > 45 - range && X < 45 + range)
                        {
                            begin= (int) (deg-X);
                        }
                        // 指向东南
                        if(X > 135 - range && X < 135 + range)
                        {
                            begin= (int) (deg-X);
                        }
                        //东南
                        if(X > 150  && X < 180)
                        {
                            begin= (int) (X-140);
                        }
                        // 指向西南
                        if(X > 225 - range && X < 225 + range)
                        {
                            begin= (int) (deg-X);
                        }
                        // 指向西北
                        if(X > 315 - range && X < 315 + range)
                        {
                            begin= (int) (deg-X);
                        }
                    }
                    break;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            Log.d(TAG, "onAccuracyChanged: "+i);
        }
    };
    class CustomView extends View {
        public CustomView(Context context) {
            super(context);
            //new 一个画笔
            paint=new Paint();
            //设置画笔颜色
            paint.setColor(Color.YELLOW);
            //设置结合处的样子,Miter:结合处为锐角, Round:结合处为圆弧:BEVEL:结合处为直线。
            paint.setStrokeJoin(Paint.Join.ROUND);
            //设置画笔笔刷类型 如影响画笔但始末端
            paint.setStrokeCap(Paint.Cap.ROUND);
            //设置画笔宽度
            paint.setStrokeWidth(3);

        }
        @Override
        public void draw(Canvas canvas) {
            double startTime=System.currentTimeMillis();   //获取开始时间
            super.draw(canvas);
            //设置屏幕颜色，也可以利用来清屏。
            canvas.drawColor(Color.rgb(122,65,255));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2); //将画布移动到屏幕中心
            canvas.drawCircle(0, 0, 300, paint); //画圆圈
            Paint tmpPaint = new Paint(paint); //小刻度画笔对象
            tmpPaint.setStrokeWidth(1);//设置画笔笔尖的粗细
            float  y=300;   //向Y方向移动画笔的位置
            float  x=300;   //向X方向移动画笔的位置
            int count = 360; //总刻度数
            canvas.save();//各个状态最初，是下次第一个canvas.restore()返回点
            canvas.rotate(begin,0f,0f); //旋转画纸，使1到12的刻度按钟表习惯写在上面。
            for(int i=0 ; i <count ; i++){
                if(i%5 == 0){
                    //60份里面5的倍数就是1-12，所以比其他刻度画得长一点，y加减控制刻度长度
                    canvas.drawLine(0f, y, 0, y+12f, paint);
                    //把1-12数字写在钟表相应位置上
                    canvas.drawText(String.valueOf(i), -4f, y+25f, tmpPaint);
                }else{
                    canvas.drawLine(0f, y, 0f, y +5f, tmpPaint);
                }
                //每一个循环就旋转一个刻度，可以想象一下就是笔不动，下面的纸旋转，那么下一次画的位置就发生改变了
                canvas.rotate(360/count,0f,0f); //旋转画纸
            }
            canvas.restore();
            //绘制钟表的中心点
            tmpPaint.setColor(Color.GRAY);
            //设置画笔宽度
            tmpPaint.setStrokeWidth(4);
            canvas.drawCircle(0, 0, 7, tmpPaint);
            tmpPaint.setStyle(Paint.Style.FILL);
            tmpPaint.setColor(Color.YELLOW);
            canvas.drawCircle(0, 0, 5, tmpPaint);
            canvas.rotate(-30,0f,0f); //调整秒针
            //绘制指针
            tmpPaint.setColor(Color.RED);
            //设置画笔宽度
            tmpPaint.setStrokeWidth(6);
            canvas.rotate(begin+210,0f,0f); //旋转画纸,每秒旋转360/12/5度
            canvas.drawLine(0, -10, 0, 250, tmpPaint);
            canvas.rotate(360/12/5);
            //设置这个是为了避免上面一系列的计算的时间影响
            double endTime=System.currentTimeMillis(); //获取结束时间
            //刷新页面
            postInvalidateDelayed((long) (1-(endTime-startTime)));
            tmpPaint.setTextSize(25);
            tmpPaint.setStrokeWidth(10);
            tmpPaint.setColor(Color.WHITE);
            canvas.drawText("South", 0f, y+50f, tmpPaint);
            canvas.drawText("North", -50f, -y-50f, tmpPaint);
            canvas.drawText("East", x+50f, -40f, tmpPaint);
            canvas.drawText("West", -x-100f, 50f, tmpPaint);
        }
    }
}

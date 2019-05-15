package com.example.tpdm_u4_tarea1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Lienzo extends View {
    Thread hilo;

    Bolitas[] bolitas;

    public Lienzo(Context context){

        super(context);
        final int cantidadBolita = (int) (Math.random() * 50) + 5;
        bolitas = new Bolitas[cantidadBolita];
        Random random=new Random();

        for (int i = 0; i < cantidadBolita; i++) {
            int x,y,rad;

            rad = random.nextInt(100) + 50;
            x = random.nextInt(200) + rad;
            y = random.nextInt(200) + rad;

            bolitas[i] = new Bolitas(x,y,rad);
        }
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < cantidadBolita; i ++) {
                        bolitas[i].movimiento();
                    }
                    try {
                        sleep(20);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    invalidate();
                }
            }
        });
        hilo.start();
    }
    public void limites(Bolitas circle, Canvas c){
        if((circle.x + circle.rad ) >= c.getWidth()){
            circle.movX *= -1; }
        if((circle.x  - circle.rad) < 1){
            circle.movX *= -1; }
        if((circle.y + circle.rad  )>= c.getHeight()){
            circle.movY *= -1; }
        if((circle.y - circle.rad ) < 1){
            circle.movY *= -1; }
    }
    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        for (Bolitas bolita : bolitas) {
            limites(bolita, canvas);
            bolita.pintarCanvas(canvas, p);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return true;
    }
}
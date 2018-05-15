# PageTransformer 

    private void initFanZhuan() {
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener lsn = new SensorEventListener() {
            public void onSensorChanged(SensorEvent e) {
                x = e.values[SensorManager.DATA_X];
                y = e.values[SensorManager.DATA_Y];
                z = e.values[SensorManager.DATA_Z];
                //                text.setText("x=" + (int) x + "," + "y=" + (int) y + "," + "z=" + (int) z);
                text.setText("x=" + (int) x);
                if (x > 1 && isStart != 200) {
                    isStart = 200;
                    playAnim();
                } else if (x < -1 && isStart != 100) {
                    isStart = 100;
                    playAnim();
                } else if (x >= -1 && x <= 1 && isStart != 0) {
                    isStart = 0;
                    bannerView.stopAutoPlay();
                    bannerView.setAutoPalyTime(2000);
                    bannerView.startAutoPlay();
                }
                Log.d("MainActivity", "x:" + x);
            }

            public void onAccuracyChanged(Sensor s, int accuracy) {
            }
        };
        //注册listener，第三个参数是检测的精确度
        sensorMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
    }
    private void playAnim() {
        Log.e("tag", "playAnim::" + isStart);
        bannerView.stopAutoPlay();
        bannerView.setIsOrder(isStart);
        bannerView.setAutoPalyTime(250);
        bannerView.startAutoPlay();
    }

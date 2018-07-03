演示图片
--------
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/elasticBall.gif)
<br>
简单使用
--------	
		在布局中加上这一段话就可以使用了
		<com.example.bezierball.custom_view.BezierBall
			android:layout_width="wrap_content"
			android:layout_height="120dp"
			android:padding="10dp" />
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/ballRadiusBig.png)
<br>

		* ballRadius 修改球的半径 默认为20dp
		<com.example.bezierball.custom_view.BezierBall
			android:layout_width="wrap_content"
			android:layout_height="120dp"
			app:ballRadius="10dp"
			android:padding="10dp" />
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/ballNumber.png)
<br>

		* ballNumber 修改球的数目 默认为1个
		<com.example.bezierball.custom_view.BezierBall
			android:layout_width="match_parent"
			android:layout_height="120dp"
			app:ballRadius="10dp"
			app:ballNumber="3"
			android:padding="10dp" />
		java 代码设置方法 对象.setBallNumber(3);
		* ballColor 修改球的颜色
		当然也可以添加一组颜色 java代码 对象.setColorList(colorList);
		//设置一下颜色列表 colorList是integer集合 格式0xaarrggbb
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/ballColor.png)
<br>
		
		* currentSelected 当前选择的view
		<com.example.bezierball.custom_view.BezierBall
			android:layout_width="match_parent"
			android:layout_height="120dp"
			app:ballRadius="10dp"
			app:ballNumber="3"
			app:currentSelected="1"
			app:ballColor="#8ca9ff"
			android:padding="10dp" />
		java 代码设置方法 对象setCurrentSelected(position);//position integer
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/ballCurrentSelected.png)
<br>
		
		* showBottomCircle 是否展示底下的圆 很简单就不演示了
		
		* bottomCircleFill 底部圆的填充色
		
		* bottomCircleStroke 底部圆的描边颜色
		
		* bottomCircleStrokeWidth 底部圆描边宽
		
		* circleTouchPadding 为了用户更好的触摸 是为了扩大单个item用户触摸范围的 默认为10dp
		
		* extensionRatio 拉伸到最长的时候的拉伸倍率 建议 [0,1]
		app:extensionRatio="0.5" 和 app:extensionRatio="1" 的效果如下 大家酌情选择
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/extendSmall.jpg)
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/extendBig.jpg)

		
		
		
		
		
       
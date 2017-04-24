# NineGridLayout
一个仿微信朋友圈和QQ空间的九宫格图片展示自定义控件。

CSDN：[http://blog.csdn.net/hmyang314/article/details/51415396](http://blog.csdn.net/hmyang314/article/details/51415396)

![](https://github.com/HMY314/NineGridLayout/blob/master/imageCache/GIF.gif)

----------
## 一、介绍
        1、当只有1张图时，可以自己定制图片宽高，也可以使用默认九宫格的宽高；
        2、当只有4张图时，以2*2的方式显示；
        3、除以上两种情况下，都是按照3列方式显示，但这时有一些细节：
            a、如果只有9张图，当然是以3*3的方式显示；
            b、如果超过9张图，可以设置是否全部显示。
                如果设置不完全显示，则按照3*3的方式显示，但是在第9张图上会有一个带“+”号的数字，
                代表还有几张没有显示，这里是模仿了QQ空间图片超出9张的显示方式；
                如果设置全部显示，理所当然的将所有图片都显示出来。
        4、图片被按下时，会有一个变暗的效果，这也是模仿微信朋友圈的效果。

----------
## 二、使用方法

1、核心类是NineGridLayout，继承自ViewGroup的抽象类，所以我们实际项目使用需要继承它，并要实现3个方法，如下：

        public abstract class NineGridLayout extends ViewGroup {
        //******************************其他代码省略**************************

            /**
             * 显示一张图片
             * @param imageView
             * @param url
             * @param parentWidth 父控件宽度
             * @return true 代表按照九宫格默认大小显示，false 代表按照自定义宽高显示
             */
            protected abstract boolean displayOneImage(RatioImageView imageView, String url, int parentWidth);

            protected abstract void displayImage(RatioImageView imageView, String url);

            /**
             * 点击图片时执行
             */
            protected abstract void onClickImage(int position, String url, List<String> urlList);
        }

----------
2、我这里用NineGridTestLayout继承NineGridLayout实现，displayOneImage()与displayImage()中的参数都是显示图片需要的，我这里用的是ImageLoader显示图片，当然你也可以用其他的。

        public class NineGridTestLayout extends NineGridLayout {

         protected static final int MAX_W_H_RATIO = 3;

         public NineGridTestLayout(Context context) {
             super(context);
         }

         public NineGridTestLayout(Context context, AttributeSet attrs) {
             super(context, attrs);
         }

         @Override
         protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

            //这里是只显示一张图片的情况，显示图片的宽高可以根据实际图片大小自由定制，parentWidth 为该layout的宽度
             ImageLoader.getInstance().displayImage(imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
                 @Override
                 public void onLoadingStarted(String imageUri, View view) {

                 }

                 @Override
                 public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                 }

                 @Override
                 public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                     int w = bitmap.getWidth();
                     int h = bitmap.getHeight();

                     int newW;
                     int newH;
                     if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                         newW = parentWidth / 2;
                         newH = newW * 5 / 3;
                     } else if (h < w) {//h:w = 2:3
                         newW = parentWidth * 2 / 3;
                         newH = newW * 2 / 3;
                     } else {//newH:h = newW :w
                         newW = parentWidth / 2;
                         newH = h * newW / w;
                     }
                     setOneImageLayoutParams(imageView, newW, newH);
                 }

                 @Override
                 public void onLoadingCancelled(String imageUri, View view) {

                 }
             });
             return false;// true 代表按照九宫格默认大小显示(此时不要调用setOneImageLayoutParams)；false 代表按照自定义宽高显示。
         }

         @Override
         protected void displayImage(RatioImageView imageView, String url) {
             ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
         }

         @Override
         protected void onClickImage(int i, String url, List<String> urlList) {
             Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
         }
        }

----------
3、在xml中实现

        <com.hmy.ninegridlayout.view.NineGridTestLayout xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/layout_nine_grid"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        app:sapcing="4dp" />

app:sapcing是设置九宫格中图片之间的间隔。

----------
4、使用：

        List<String> urlList = new ArrayList<>();//图片url
        NineGridTestLayout layout = (NineGridTestLayout) view.findViewById(R.id.layout_nine_grid);
        layout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
        layout.setSpacing(5); //动态设置图片之间的间隔
        layout.setUrlList(urlList); //最后再设置图片url

----------
5、效果图

![](https://github.com/HMY314/NineGridLayout/blob/master/imageCache/img1.png)
![](https://github.com/HMY314/NineGridLayout/blob/master/imageCache/img2.png)
![](https://github.com/HMY314/NineGridLayout/blob/master/imageCache/img3.png)
![](https://github.com/HMY314/NineGridLayout/blob/master/imageCache/img4.png)
![](https://github.com/HMY314/NineGridLayout/blob/master/imageCache/img5.png)

package com.ht.fyforandroid.net.img;

/**
 * Created by niehongtao on 16/7/21.
 */
public class ImageLoaderFactory {
    public static final int TPYE_UNIVERSAL_IMAGE_LOADER = 1;
    public static final int TPYE_GLIDE = 2;
    public static final int TPYE_PICASSO = 3;


    public static ImageLoaderProduct createImageLoader(int type) {
        ImageLoaderProduct product = null;
        switch (type) {
            case TPYE_GLIDE:
                product = GlideLoaderProduct.getInstance();
                break;
            case TPYE_PICASSO:
                product = PicassoLoaderProduct.getInstance();
                break;
            case TPYE_UNIVERSAL_IMAGE_LOADER:
                product = UILoaderProduct.getInstance();
                break;
        }
        return product;
    }
}

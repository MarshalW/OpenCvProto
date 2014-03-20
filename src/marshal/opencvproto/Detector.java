package marshal.opencvproto;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Detector {

	private Mat mat;

	/**
	 * 假的方法，验证从bitmap->Mat(java)->Mat(c++)
	 * 
	 * @param bitmap
	 * @return
	 */
	public boolean isSameImage(Bitmap bitmap) {
		mat = new Mat();
		Utils.bitmapToMat(bitmap, mat);
		detect(mat.nativeObj);
		return true;
	}

	/**
	 * 生成灰度直方图矩阵
	 * 
	 * @param bitmap
	 * @return
	 */
	public Mat generateHistogram(Bitmap bitmap) {
		Mat bitmapMat = new Mat();
		Utils.bitmapToMat(bitmap, bitmapMat);

		// Mat histogram = new Mat(bitmapMat.size(), Imgproc.COLOR_RGB2GRAY);
		long address = generateHistogram(bitmapMat.nativeObj);
		Mat histogram = new Mat(address);

		Log.d("opencv_detector", "mat: \n " + matToJson(histogram));

		// Log.d("opencv_detector", histogram.size().toString());
		return histogram;
	}

	private native void detect(long mat);

	private native long generateHistogram(long bitmap);

	static {
		System.loadLibrary("opencv_java");
		System.loadLibrary("Detector");
	}

	public static String matToJson(Mat _mat) {
		JsonObject obj = new JsonObject();
		Mat mat=new Mat();
		mat.assignTo(mat,CvType.CV_8UC1);

		if (mat.isContinuous()||true) {
			int cols = mat.cols();
			int rows = mat.rows();
			int elemSize = (int) mat.elemSize();

			int[] data = new int[cols * rows * elemSize];

			Log.d("opencv_detector", ">>>>cols: " + cols + ", rows: " + rows
					+ "\n mat: " + mat.dump());
			Log.d("opencv_detector", ">>>mat.type: "+mat.type());
			
			mat.get(0, 0, data);
//			double[] data=m/at.get(0, 0);
			
			Log.d("opencv_detector", ">>>double[] data.length: "+data.length);
			
			//
			// obj.addProperty("rows", mat.rows());
			// obj.addProperty("cols", mat.cols());
			// obj.addProperty("type", mat.type());
			//
			// // We cannot set binary data to a json object, so:
			// // Encoding data byte array to Base64.
			// String dataString = new String(Base64.encode(data,
			// Base64.DEFAULT));
			//
			// obj.addProperty("data", dataString);

			Gson gson = new Gson();
			String json = gson.toJson(obj);

			return json;
		} else {
			Log.e("opencv_detector", "Mat not continuous.");
		}
		return "{}";
	}
}

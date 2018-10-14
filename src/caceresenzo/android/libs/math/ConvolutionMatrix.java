package caceresenzo.android.libs.math;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ConvolutionMatrix {
	public static final int SIZE = 3;
	
	public double[][] Matrix;
	public double Factor = 1;
	public double Offset = 1;
	
	public ConvolutionMatrix(int size) {
		Matrix = new double[size][size];
	}
	
	public void setAll(double value) {
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				Matrix[x][y] = value;
			}
		}
	}
	
	public void applyConfig(double[][] config) {
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				Matrix[x][y] = config[x][y];
			}
		}
	}
	
	public static Bitmap computeConvolution3x3(Bitmap source, ConvolutionMatrix matrix) {
		int width = source.getWidth();
		int height = source.getHeight();
		Bitmap result = Bitmap.createBitmap(width, height, source.getConfig());
		
		int alpha, red, green, blue;
		int sumRed, sumGreen, sumBlue;
		int[][] pixels = new int[SIZE][SIZE];
		
		for (int y = 0; y < height - 2; ++y) {
			for (int x = 0; x < width - 2; ++x) {
				
				// Get pixel matrix
				for (int i = 0; i < SIZE; ++i) {
					for (int j = 0; j < SIZE; ++j) {
						pixels[i][j] = source.getPixel(x + i, y + j);
					}
				}
				
				// Get alpha of center pixel
				alpha = Color.alpha(pixels[1][1]);
				
				// Initialize color sum
				sumRed = sumGreen = sumBlue = 0;
				
				// Get sum of RGB on matrix
				for (int i = 0; i < SIZE; ++i) {
					for (int j = 0; j < SIZE; ++j) {
						sumRed += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
						sumGreen += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
						sumBlue += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
					}
				}
				
				// Get final rgb
				red = englobeColor((int) (sumRed / matrix.Factor + matrix.Offset));
				green = englobeColor((int) (sumGreen / matrix.Factor + matrix.Offset));
				blue = englobeColor((int) (sumBlue / matrix.Factor + matrix.Offset));
				
				// Apply new pixel
				result.setPixel(x + 1, y + 1, Color.argb(alpha, red, green, blue));
			}
		}
		
		source.recycle();
		source = null;
		
		return result;
	}
	
	private static int englobeColor(int value) {
		if (value < 0) {
			return 0;
		} else if (value > 255) {
			return 255;
		}
		return value;
	}
	
}
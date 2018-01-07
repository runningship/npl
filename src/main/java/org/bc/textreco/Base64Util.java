package org.bc.textreco;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Base64Util {

	public static String decode(BufferedImage bi) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();    
		ImageIO.write(bi, "png", baos);    
		byte[] bytes = baos.toByteArray();

		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		String base64Data = encoder.encodeBuffer(bytes).trim();
		return base64Data;
	}
}

package org.colorMine.tests.profile.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.colorMine.colorSpace.Rgb;
import org.colorMine.profile.*;
import org.colorMine.profile.filter.IColorProfileFilterResult;
import org.colorMine.profile.filter.MapFilter;

@Test
public class MapFilterTest {

	private IColoredImage _image;
	@BeforeTest
	public void setup() {
		
		 _image = mock(IColoredImage.class);
		when(_image.getHeight()).thenReturn(1);
		when(_image.getWidth()).thenReturn(1);
		when(_image.getRGB(0,0)).thenReturn(0xFF0000);

	}
	
	
	@Test
	public void Sanity() {
		
		//ARRANGE
		Map<Rgb, Integer> colors = new HashMap<Rgb, Integer>();
		colors.put(new Rgb(new double[] { 1, 0, 0 }), 1);
		IColorProfile map = new ColorProfile(colors);
		
		
		MapFilter filter = new MapFilter(map);
		IColorProfile imageProfile = new ColorProfile(_image);
		IColorProfileFilterResult result = filter.apply(imageProfile);
		
		AssertJUnit.assertTrue(result.getOriginalProfile() == imageProfile);
		AssertJUnit.assertTrue(0 == result.getDiscardedProfile().getRgbProfile().size());
		AssertJUnit.assertTrue(1 == result.getFilteredProfile().getRgbProfile().size());
	}

	public void BasicRounding() {
		
		//ARRANGE
		Map<Rgb, Integer> colors = getBasicProfile();
		IColorProfile map = new ColorProfile(colors);

		
		MapFilter filter = new MapFilter(map);
		IColorProfile imageProfile = new ColorProfile(_image);
		IColorProfileFilterResult result = filter.apply(imageProfile);
		Map<Rgb, Integer> rgbColors = result.getFilteredProfile().getRgbProfile();
		
		AssertJUnit.assertTrue(rgbColors.containsKey(new Rgb(.5, 0, 0)));
	}

	private Map<Rgb, Integer> getBasicProfile() {
		Map<Rgb, Integer> colors = new HashMap<Rgb, Integer>();
		colors.put(new Rgb(new double[] { .5, 0, 0 }), 1);
		colors.put(new Rgb(new double[] { 0, .5, 0 }), 1);
		colors.put(new Rgb(new double[] { 0, 0, .5 }), 1);
		return colors;
	}

}
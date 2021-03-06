package org.colormine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;

import org.colormine.colorspace.ColorSpaceConverter;
import org.colormine.image.Image;
import org.colormine.image.profile.Profile;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class ColorMineTest {

	private Image _image;

	@BeforeTest
	public void setup() {
		_image = mock(Image.class);
		when(_image.getHeight()).thenReturn(1);
		when(_image.getWidth()).thenReturn(1);
		when(_image.getRGB(0, 0)).thenReturn(0xFF0000);
	}

	public void getColorProfile_givenHeightAndWitdh1_returns1AsValue() {
		Profile<Color> result = ColorMine.getColorProfile(_image);

		AssertJUnit.assertEquals(1, result.size());
	}

	public void getRgbProfile_givenColorX_returnColorX_AsKey() {
		Profile<Color> result = ColorMine.getColorProfile(_image);

		AssertJUnit.assertEquals(1, (int) result.get(Color.red));
	}

	public void isComplement_givenComplementaryColors_returnsTrue() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);
		Color color2 = new Color(238, 144, 237);

		// ACT
		boolean result = ColorMine.isComplement(color1, color2);

		// ASSERT
		AssertJUnit.assertEquals(true, result);
	}

	public void isComplement_givenNearComplementaryColorsAndNoTolerance_returnsFalse() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);
		Color color2 = new Color(238, 145, 237);

		// ACT
		boolean result = ColorMine.isComplement(color1, color2, 0);

		// ASSERT
		AssertJUnit.assertEquals(false, result);
	}

	public void isComplement_givenNearComplementaryColorsAndSmallTolerance_returnsTrue() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);
		Color color2 = new Color(238, 145, 237);

		// ACT
		boolean result = ColorMine.isComplement(color1, color2, 1);

		// ASSERT
		AssertJUnit.assertEquals(true, result);
	}

	public void isComplement_givenNonComplementaryColors_returnsFalse() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);
		Color color2 = new Color(0, 0, 255);

		// ACT
		boolean result = ColorMine.isComplement(color1, color2);

		// ASSERT
		AssertJUnit.assertEquals(false, result);
	}

	public void getTriadic_givenAColor_returnsTwoValues() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);

		// ACT
		Color[] result = ColorMine.getTriadic(color1);

		// ASSERT
		AssertJUnit.assertEquals(2, result.length);
	}

	public void getAnalogous_givenAColor_returnsTwoValues() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);

		// ACT
		Color[] result = ColorMine.getAnalogous(color1);

		// ASSERT
		AssertJUnit.assertEquals(2, result.length);
	}

	public void getSpiltComplements_givenAColor_returnsTwoValues() {

		// ARRANGE
		Color color1 = new Color(144, 238, 144);

		// ACT
		Color[] result = ColorMine.getSplitComplements(color1);

		// ASSERT
		AssertJUnit.assertEquals(2, result.length);
	}

	public void getTriadic_givenAColor_returnsTriadicColors() {

		// ARRANGE
		Color color1 = new Color(255, 0, 111);
		Color expectedColor1 = new Color(111, 255, 0);
		Color expectedColor2 = new Color(0, 110, 255);

		// ACT
		Color[] result = ColorMine.getTriadic(color1);

		// ASSERT
		AssertJUnit.assertTrue(contains(expectedColor1, result) && contains(expectedColor2, result));
	}

	public void getAnalogous_givenAColor_returnsAnalogousColors() {

		// ARRANGE
		Color color1 = new Color(255, 0, 111);
		Color expectedColor1 = new Color(255, 16, 0);
		Color expectedColor2 = new Color(255, 0, 238);

		// ACT
		Color[] result = ColorMine.getAnalogous(color1);

		// ASSERT
		AssertJUnit.assertTrue(contains(expectedColor1, result) && contains(expectedColor2, result));
	}

	public void getSpiltComplements_givenAColor_returnsSpiltComplements() {

		// ARRANGE
		Color color1 = new Color(255, 0, 111);
		Color expectedColor1 = new Color(0, 255, 16);
		Color expectedColor2 = new Color(0, 238, 255);

		// ACT
		Color[] result = ColorMine.getSplitComplements(color1);

		// ASSERT
		AssertJUnit.assertTrue(contains(expectedColor1, result) && contains(expectedColor2, result));
	}

	public void calculateSimilarity_givenExactMatchingColors_returnsZero() {
		// ARRANGE
		Color firstColor = new Color(255, 0, 0);
		Color secondColor = new Color(255, 0, 0);

		// ACT
		double result = ColorMine.calculateSimilarity(firstColor, secondColor);
		// ASSERT
		AssertJUnit.assertEquals(result, 0.0);
	}

	// Helpers
	private boolean contains(Color color, Color[] colors) {
		for (Color c : colors) {
			if (ColorSpaceConverter.isNearMatch(c, color, 1.0)) {
				return true;
			}
		}
		return false;
	}

}
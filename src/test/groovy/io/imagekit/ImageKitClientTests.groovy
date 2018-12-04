package io.imagekit

import org.junit.Test

class ImageKitClientTests {

    private String uploadTestFile = 'test.png'
    private String testUrl = ''

    @Test
    void uploadTest() {
        ImageKitClient client = new ImageKitClient()
        def result = client.uploadFile(uploadTestFile)
        println result
    }

    @Test
    void getLibraryTest() {
        ImageKitClient client = new ImageKitClient()
        def result = client.getLibrary(0)
        println result
    }

    @Test
    void getFileByNameTest() {
        ImageKitClient client = new ImageKitClient()
        def result = client.getFileByName('')
        println result
    }

    @Test
    void testCropImage() {
        ImageKitClient client = new ImageKitClient()
        def result = client.crop(testUrl, 300, 469)
        println result
    }

    @Test
    void testImageQuality() {
        ImageKitClient client = new ImageKitClient()
        def result = client.quality(testUrl, 5)
        println result
    }

    @Test
    void testCropImageWithQuality() {
        ImageKitClient client = new ImageKitClient()
        def result = client.crop(testUrl, 300, 469)
        println result
    }

    @Test
    void testRotateImage() {
        ImageKitClient client = new ImageKitClient()
        def result = client.rotate(testUrl, 90)
        println result
    }

    @Test
    void testRadiusImage() {
        ImageKitClient client = new ImageKitClient()
        def result = client.radius(testUrl, 20)
        println result
    }

    @Test
    void testBlurImage() {
        ImageKitClient client = new ImageKitClient()
        def result = client.blur(testUrl, 20)
        println result
    }

    @Test
    void testBorderImage() {
        ImageKitClient client = new ImageKitClient()
        def result = client.border(testUrl, 2, '000000')
        println result
    }

}

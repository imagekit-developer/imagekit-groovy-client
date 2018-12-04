package io.imagekit

import org.junit.Test

class ImageKitClientTests {

    private String uploadTestFile = 'test.png'

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
        def result = client.cropImage('', 300, 469)
        println result
    }

    @Test
    void testImageQuality() {
        ImageKitClient client = new ImageKitClient()
        def result = client.imageQuality('', 5)
        println result
    }

    @Test
    void testCropImageWithQuality() {
        ImageKitClient client = new ImageKitClient()
        def result = client.cropImage('', 300, 469, 5)
        println result
    }

}

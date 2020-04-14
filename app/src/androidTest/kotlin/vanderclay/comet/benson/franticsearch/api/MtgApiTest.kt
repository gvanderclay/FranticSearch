package vanderclay.comet.benson.franticsearch.api

import io.magicthegathering.javasdk.api.CardAPI
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

private const val FAKE_STRING = "HELLO WORLD"

@RunWith(MockitoJUnitRunner::class)
class UnitTestSample {

    @Mock
    private lateinit var mockCardApi: CardAPI

    @Test
    fun readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        //        `when`(mockContext.getString(R.string.hello_word)).thenReturn(FAKE_STRING)
        //        val myObjectUnderTest = ClassUnderTest(mockContext)

        // ...when the string is returned from the object under test...
        //        val result: String = myObjectUnderTest.getHelloWorldString()

        // ...then the result should be the expected one.
//        assertThat(result, `is`(FAKE_STRING))
    }
}
@file:OptIn(ExperimentalCoroutinesApi::class)

package fr.mcgalanes.rectus.core.testing.rule

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * [MainCoroutineScopeRule] installs a [TestDispatcher] for [Dispatchers.Main].
 * @see <a href="https://developer.android.com/kotlin/coroutines/test">Android documentation</a>
 * @see <a href="https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test">Kotlin documentation</a>
 */
class MainCoroutineScopeRule(
    val dispatcher: TestDispatcher
) : TestWatcher(), CoroutineScope by TestScope(dispatcher) {
    override fun starting(description: Description) = Dispatchers.setMain(dispatcher)
    override fun finished(description: Description) = Dispatchers.resetMain()
}

@DelicateCoroutinesApi
fun MainCoroutineScopeRule(): MainCoroutineScopeRule = UnconfinedCoroutineScopeRule()

/**
 * @see [MainCoroutineScopeRule]
 * @see [UnconfinedTestDispatcher]
 */
@Suppress("FunctionName")
fun UnconfinedCoroutineScopeRule(
    scope: TestScope? = null
): MainCoroutineScopeRule = MainCoroutineScopeRule(UnconfinedTestDispatcher(scheduler = scope?.testScheduler))

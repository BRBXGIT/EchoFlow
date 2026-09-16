package com.brbx.onboarding

import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.use_case.AuthenticateUserUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.home.HomeRoute
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingState
import com.brbx.onboarding.view_model.AuthenticatorImpl
import com.brbx.onboarding.view_model.OnboardingMviScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

// TODO Make tests passable
@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticatorTest {

    private val testDispatcher = StandardTestDispatcher()
    private val fakeAuthUseCase = FakeAuthenticateUserUseCase()

    @Test
    fun `when authenticate succeeds then updates loading state and posts navigate effects`() =
        runTest(context = testDispatcher) {
            val scope = FakeOnboardingMviScope(viewModelScope = this)
            val authenticator = AuthenticatorImpl(
                scope = scope,
                authUseCase = fakeAuthUseCase,
                dispatcherDefault = testDispatcher,
            )

            val targetDeeplink = "https://echoflow-e6849.web.app/?code=valid_code_123&state=123"
            fakeAuthUseCase.resultToReturn = success(value = Unit)

            authenticator(intent = OnboardingIntent.Authenticate(deeplink = targetDeeplink))

            advanceTimeBy(delayTime = 1_500.milliseconds)

            assertEquals(expected = targetDeeplink, actual = fakeAuthUseCase.lastRawUri)
            assertFalse(actual = scope.state.value.loading)

            val firstEffect = scope.effects.first()
            assertEquals(expected = EchoFlowEffect.Navigate(key = HomeRoute), actual = firstEffect)
        }

    @Test
    fun `when authenticate fails then posts snackbar effect and resets loading state`() =
        runTest(context = testDispatcher) {
            val scope = FakeOnboardingMviScope(viewModelScope = this)
            val authenticator = AuthenticatorImpl(
                scope = scope,
                authUseCase = fakeAuthUseCase,
                dispatcherDefault = testDispatcher,
            )

            val targetDeeplink = "https://echoflow-e6849.web.app/?code=invalid_code_123&state=123"
            fakeAuthUseCase.resultToReturn = failure(exception = RequestException.Internet)

            authenticator(intent = OnboardingIntent.Authenticate(deeplink = targetDeeplink))

            advanceTimeBy(delayTime = 1_500.milliseconds)

            assertFalse(actual = scope.state.value.loading)

            val firstEffect = scope.effects.first()
            assertTrue(actual = firstEffect is EchoFlowEffect.Snackbar)
        }

    private class FakeOnboardingMviScope(
        override val viewModelScope: CoroutineScope,
    ) : OnboardingMviScope<Any?> {

        private val _state = MutableStateFlow(value = OnboardingState<Any?>())
        override val state: StateFlow<OnboardingState<Any?>> = _state.asStateFlow()

        private val _effects = MutableSharedFlow<EchoFlowEffect>(extraBufferCapacity = 64)
        override val effects: SharedFlow<EchoFlowEffect> = _effects.asSharedFlow()

        private val _screenEffects = MutableSharedFlow<Unit>(extraBufferCapacity = 64)
        override val screenEffects: SharedFlow<Unit> = _screenEffects.asSharedFlow()

        override fun reduce(reducer: OnboardingState<Any?>.() -> OnboardingState<Any?>) {
            _state.value = _state.value.reducer()
        }

        override fun dispatchIntent(intent: OnboardingIntent) {}

        override fun postEffect(effect: EchoFlowEffect) {
            _effects.tryEmit(value = effect)
        }

        override fun postScreenEffect(effect: Unit) {
            _screenEffects.tryEmit(value = effect)
        }
    }

    private class FakeAuthenticateUserUseCase : AuthenticateUserUseCase {
        var lastRawUri: String? = null
        var resultToReturn: RequestResult<Unit> = success(value = Unit)

        override suspend fun invoke(rawUri: String): RequestResult<Unit> {
            lastRawUri = rawUri
            return resultToReturn
        }
    }
}
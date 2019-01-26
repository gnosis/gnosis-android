package pm.gnosis.heimdall.ui.settings.general.payment

import android.content.Context
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pm.gnosis.heimdall.data.repositories.TokenRepository
import pm.gnosis.heimdall.data.repositories.models.ERC20Token
import pm.gnosis.heimdall.di.ApplicationContext
import pm.gnosis.heimdall.helpers.PasswordHelper
import pm.gnosis.heimdall.helpers.PasswordValidationCondition
import pm.gnosis.heimdall.ui.base.Adapter
import pm.gnosis.heimdall.ui.exceptions.SimpleLocalizedException
import pm.gnosis.svalinn.common.utils.Result
import pm.gnosis.svalinn.common.utils.mapToResult
import pm.gnosis.svalinn.security.EncryptionManager
import javax.inject.Inject

class PaymentTokenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenRepository: TokenRepository
) : PaymentTokenContract() {

    private val errorHandler = SimpleLocalizedException.networkErrorHandlerBuilder(context).build()

    override fun loadPaymentTokens(): Single<Adapter.Data<ERC20Token>> =
        tokenRepository.loadPaymentTokens().map {
            Adapter.Data(entries = it)
        }
            .onErrorResumeNext { errorHandler.single(it) }

    override fun setPaymentToken(token: ERC20Token): Single<Result<ERC20Token>> =
        tokenRepository.setPaymentToken(token)
            .andThen(Single.just(token))
            .mapToResult()
}
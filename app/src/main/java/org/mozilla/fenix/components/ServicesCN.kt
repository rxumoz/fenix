package org.mozilla.fenix.components

/**
 * Created by mozillabeijing on 2019/4/18.
 */
import mozilla.components.service.fxa.manager.FxaAccountManager
import org.mozilla.fenix.components.features.FirefoxAccountsAuthFeature
import org.mozilla.fenix.test.Mockable
/**
 * Component group which encapsulates foreground-friendly services.
 */
class ServicesCN(
        private val accountManager: FxaAccountManager
) {
    val accountsAuthFeature by lazy {
        FirefoxAccountsAuthFeature(
                accountManager,
                redirectUrl = BackgroundServices.REDIRECT_URL_CN
        )
    }
}
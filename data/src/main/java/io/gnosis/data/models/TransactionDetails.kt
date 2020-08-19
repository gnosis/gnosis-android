package io.gnosis.data.models

import io.gnosis.data.backend.dto.DataDecodedDto
import io.gnosis.data.backend.dto.Operation
import io.gnosis.data.backend.dto.TransactionDirection
import pm.gnosis.model.Solidity
import java.math.BigInteger
import java.util.*

data class TransactionDetails(
    val txHash: String?,
    val txStatus: TransactionStatus,
    val txInfo: TransactionInfo,
    val executedAt: Date?,
    val txData: TxData?,
    val detailedExecutionInfo: DetailedExecutionInfo?
)

data class TxData(
    val hexData: String?,
    val dataDecoded: DataDecodedDto?,
    val to: Solidity.Address,
    val value: BigInteger?,
    val operation: Operation
)

sealed class DetailedExecutionInfo {
    data class MultisigExecutionDetails(
        val submittedAt: Date,
        val nonce: BigInteger,
        val safeTxHash: String,
        val signers: List<Solidity.Address>,
        val confirmationsRequired: Int,
        val confirmations: List<Confirmations>
    ) : DetailedExecutionInfo()

    data class ModuleExecutionDetails(
        val address: String
    ) : DetailedExecutionInfo()
}

sealed class TransactionInfo {
    data class Custom(
        val to: Solidity.Address,
        val dataSize: Int,
        val value: String
    ) : TransactionInfo()

    data class SettingsChange(
        val dataDecoded: DataDecodedDto
    ) : TransactionInfo()

    data class Transfer(
        val sender: Solidity.Address,
        val recipient: Solidity.Address,
        val transferInfo: TransferInfo,
        val direction: TransactionDirection
    ) : TransactionInfo()

    object Creation : TransactionInfo()

    object Unknown : TransactionInfo()
}

sealed class TransferInfo {
    data class Erc20Transfer(
        val tokenAddress: Solidity.Address,
        val tokenName: String?,
        val tokenSymbol: String?,
        val logoUri: String?,
        val decimals: Int?,
        val value: String
    ) : TransferInfo()

    data class Erc721Transfer(
        val tokenAddress: Solidity.Address,
        val tokenId: String,
        val tokenName: String?,
        val tokenSymbol: String?,
        val logoUri: String?
    ) : TransferInfo()

    data class EtherTransfer(
        val value: String
    ) : TransferInfo()
}

data class Confirmations(
    val signer: Solidity.Address,
    val signature: String
)
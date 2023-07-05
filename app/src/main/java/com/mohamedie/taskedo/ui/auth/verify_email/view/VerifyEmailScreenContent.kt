package com.mohamedie.taskedo.ui.auth.verify_email.view

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.auth.verify_email.view.event.VerifyEmailEvent
import com.mohamedie.taskedo.ui.auth.verify_email.view.state.VerifyEmailState
import com.mohamedie.taskedo.ui.common.component.RemoteErrorHeader
import com.mohamedie.taskedo.ui.theme.TaskedoTheme
import com.mohamedie.taskedo.utils.taskedoLoading

@Composable
fun VerifyEmailScreenContent(state: VerifyEmailState, onEvent: (VerifyEmailEvent) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        RemoteErrorHeader(state.remoteError?.asString())
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.verify_your_email),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.we_have_sent_an_email_to))
                append(" ")
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = .5f),
                        fontWeight = FontWeight.Bold
                    )
                ) { append(state.email) }
                append(" ")
                append(stringResource(id = R.string.to_verify_your_email_address_and_activate_your_account))
            },
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.weight(1f))

        Row {
            OutlinedButton(
                shape = MaterialTheme.shapes.medium,
                enabled = !state.isLoading,
                border = BorderStroke(0.dp, Color.Transparent),
                onClick = { onEvent(VerifyEmailEvent.ResendVerificationEmail) }
            ) {
                Text(
                    text = stringResource(id = R.string.resend_email),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(0.dp, Color.Transparent),
                onClick = { onEvent(VerifyEmailEvent.ChangeEmail) }
            ) {
                Text(
                    text = stringResource(id = R.string.change_email),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            enabled = !state.isLoading,
            onClick = {
                val intent = Intent(Intent.ACTION_MAIN)
                    .addCategory(Intent.CATEGORY_APP_EMAIL)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val title = context.getString(R.string.select_email_app)
                context.startActivity(Intent.createChooser(intent, title))
            }
        ) {
            Text(
                modifier = Modifier.taskedoLoading(state.isLoading),
                text = stringResource(id = R.string.open_email),
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview
@Composable
fun PreviewVerifyEmailScreenContent() {
    TaskedoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            VerifyEmailScreenContent(VerifyEmailState("Mohammedie98@gmail.com"), {})
        }
    }
}
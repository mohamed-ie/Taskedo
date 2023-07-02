package com.mohamedie.taskedo.ui.auth.sign_up.view

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Visibility
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.auth.AuthGraph
import com.mohamedie.taskedo.ui.auth.sign_up.view.event.SignUpEvent
import com.mohamedie.taskedo.ui.auth.sign_up.view.state.SignUpState
import com.mohamedie.taskedo.ui.common.component.RemoteErrorHeader
import com.mohamedie.taskedo.ui.common.component.TaskedoOutlinedTextField
import com.mohamedie.taskedo.ui.common.component.TextFieldLayout
import com.mohamedie.taskedo.ui.theme.TaskedoTheme
import com.mohamedie.taskedo.utils.taskedoLoading
import org.koin.compose.koinInject

@Composable
fun SignUpScreenContent(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    navigateTo: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = koinInject<ActivityResultContract<Intent, Task<GoogleSignInAccount>?>>(),
        onResult = { onEvent(SignUpEvent.OnSignInWithGoogleResult(it)) }
    )
    val googleSignInIntent = koinInject<Intent>()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RemoteErrorHeader(error = state.remoteError?.asString())

        Image(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )

        val email = state.email
        TextFieldLayout(
            label = stringResource(id = R.string.email),
            error = email.error?.asString(),
            textField = {
                TaskedoOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email.value,
                    readOnly = state.isLoading,
                    onValueChange = { onEvent(SignUpEvent.EmailChanged(it)) },
                    shape = MaterialTheme.shapes.medium,
                    placeholder = { Text(text = stringResource(id = R.string.email_placeholder)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    })
                )
            })
        Spacer(modifier = Modifier.height(16.dp))

        val password = state.password
        TextFieldLayout(
            label = stringResource(id = R.string.password),
            error = password.error?.asString(),
            textField = {
                TaskedoOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password.value,
                    readOnly = state.isLoading,
                    onValueChange = { onEvent(SignUpEvent.PasswordChanged(it)) },
                    shape = MaterialTheme.shapes.medium,
                    visualTransformation = if (password.isTrailingIconVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    trailingIcon = {
                        IconButton(onClick = { onEvent(SignUpEvent.TogglePasswordVisibility) }) {
                            Icon(
                                imageVector = if (password.isTrailingIconVisible) Icons.TwoTone.Visibility else Icons.TwoTone.VisibilityOff,
                                contentDescription = stringResource(id = R.string.toggle_password_visibility)
                            )
                        }
                    }
                )
            })
        Spacer(modifier = Modifier.height(16.dp))

        val confirmPassword = state.confirmPassword
        TextFieldLayout(
            label = stringResource(id = R.string.confirm_password),
            error = confirmPassword.error?.asString()
        ) {
            TaskedoOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmPassword.value,
                readOnly = state.isLoading,
                onValueChange = { onEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
                shape = MaterialTheme.shapes.medium,
                visualTransformation = if (confirmPassword.isTrailingIconVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = { onEvent(SignUpEvent.CreateAccount) }),
                trailingIcon = {
                    IconButton(onClick = { onEvent(SignUpEvent.ToggleConfirmPasswordVisibility) }) {
                        Icon(
                            imageVector = if (confirmPassword.isTrailingIconVisible) Icons.TwoTone.Visibility else Icons.TwoTone.VisibilityOff,
                            contentDescription = stringResource(id = R.string.toggle_password_visibility)
                        )
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            shape = MaterialTheme.shapes.medium,
            onClick = { onEvent(SignUpEvent.CreateAccount) }
        ) {
            Text(
                modifier = Modifier.taskedoLoading(state.isLoading),
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Divider(Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.or),
                color = MaterialTheme.colorScheme.outline,
            )
            Divider(Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            onClick = { googleSignInLauncher.launch(googleSignInIntent) },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.height(16.dp),
                    painter = painterResource(id = R.drawable.google_g_logo),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(id = R.string.sign_up_with_google),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        val annotatedText = buildAnnotatedString {
            append(stringResource(id = R.string.already_have_an_account))
            append(" ")
            pushStringAnnotation(
                tag = "sign_in",
                annotation = stringResource(id = R.string.sign_in)
            )
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(id = R.string.sign_in))
            }
            pop()
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "sign_in",
                    start = offset,
                    end = offset
                )[0].let { navigateTo(AuthGraph.SIGN_IN) }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreenContent() {
    TaskedoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SignUpScreenContent(SignUpState(), {}, {})
        }
    }
}

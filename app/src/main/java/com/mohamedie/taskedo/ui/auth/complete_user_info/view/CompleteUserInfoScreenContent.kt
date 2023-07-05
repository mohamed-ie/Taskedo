package com.mohamedie.taskedo.ui.auth.complete_user_info.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.auth.complete_user_info.view.event.CompleteUserInfoEvent
import com.mohamedie.taskedo.ui.auth.complete_user_info.view.state.CompleteUserInfoState
import com.mohamedie.taskedo.ui.common.component.LabeledContent
import com.mohamedie.taskedo.ui.common.component.RemoteErrorHeader
import com.mohamedie.taskedo.ui.common.component.TaskedoOutlinedTextField
import com.mohamedie.taskedo.ui.theme.TaskedoTheme
import com.mohamedie.taskedo.utils.taskedoLoading

@Composable
fun CompleteUserInfoScreenContent(
    state: CompleteUserInfoState,
    onEvent: (CompleteUserInfoEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { onEvent(CompleteUserInfoEvent.PhotoUriChanged(it)) }
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RemoteErrorHeader(error = state.remoteError?.asString())
        Spacer(modifier = Modifier.weight(1f))
        Box {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .aspectRatio(1f)
                    .border(5.dp, MaterialTheme.colorScheme.surface.copy(alpha = .2f), CircleShape)
                    .clip(CircleShape),
                model = state.photoUri,
                contentScale = ContentScale.Crop,
                error = {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.outlineVariant)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(64.dp),
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    }
                },
                contentDescription = null
            )
            IconButton(modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .align(Alignment.BottomEnd),
                enabled = !state.isLoading,
                onClick = {
                    singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = stringResource(id = R.string.add_profile_photo)
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        val displayName = state.displayName
        LabeledContent(
            label = stringResource(id = R.string.display_name),
            error = displayName.error?.asString()
        ) {
            TaskedoOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = displayName.value,
                readOnly = state.isLoading,
                onValueChange = { onEvent(CompleteUserInfoEvent.DisplayNameChanged(it)) },
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = stringResource(id = R.string.display_name_placeholder)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                })
            )
        }
        Spacer(Modifier.height(16.dp))

        val userName = state.username
        LabeledContent(
            label = stringResource(id = R.string.username),
            error = userName.error?.asString()
        ) {
            TaskedoOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userName.value,
                readOnly = state.isLoading,
                onValueChange = { onEvent(CompleteUserInfoEvent.UserNameChanged(it)) },
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = stringResource(id = R.string.username_placeholder)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                })
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            enabled = !state.isLoading,
            onClick = { onEvent(CompleteUserInfoEvent.Save) }) {
            Text(
                modifier = Modifier.taskedoLoading(state.isLoading),
                text = stringResource(id = R.string.save),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1.5f))
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun PreviewCompleteUserInfoScreenContent() {
    TaskedoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CompleteUserInfoScreenContent(CompleteUserInfoState()) {}
        }
    }
}


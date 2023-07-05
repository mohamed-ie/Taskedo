package com.mohamedie.taskedo.ui.common.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.theme.TaskedoTheme

@Composable
fun TaskedoAlertDialog(
    visible: Boolean,
    title: String? = null,
    message: String? = null,
    confirmMessage: String = stringResource(id = R.string.confirm),
    cancelMessage: String = stringResource(id = R.string.cancel),
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if (visible)
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 8.dp)
                ) {

                    if (title != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    if (message != null) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            modifier = Modifier,
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(0.dp, Color.Transparent),
                            onClick = onCancel
                        ) {
                            Text(
                                text = cancelMessage,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier,
                            shape = MaterialTheme.shapes.medium,
                            onClick = onConfirm
                        ) {
                            Text(
                                text = confirmMessage,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
}

@Preview
@Composable
fun PreviewErrorDialog(){
    TaskedoTheme {
        TaskedoAlertDialog(visible = true)
    }
}

package com.example.kingburguer.compose.singup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.compose.component.KingAlert
import com.example.kingburguer.compose.component.KingButton
import com.example.kingburguer.compose.component.KingTextField
import com.example.kingburguer.compose.component.KingTextTitle
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.factory),
    onNavigationClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.login)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigationClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { contentPadding ->
            SignUpContentScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                onNavigateToLogin = onNavigateToLogin
            )
        }
    }
}

@Composable
private fun SignUpContentScreen(
    modifier: Modifier,
    viewModel: SignUpViewModel,
    onNavigateToLogin: () -> Unit
) {
    Surface(
        modifier = modifier
    ) {
        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }
        var confirmPasswordHidden by remember { mutableStateOf(true) }
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                if (uiState.goToLogin) {
                    KingAlert(
                        onDismissRequest = {},
                        onConfirmation = {
                            onNavigateToLogin()
                            viewModel.reset()
                        },
                        dialogTitle = stringResource(R.string.app_name),
                        dialogText = stringResource(R.string.user_created),
                        icon = Icons.Filled.Info
                    )
                }



                uiState.error?.let {
                    KingAlert(
                        onDismissRequest = {

                        },
                        onConfirmation = {
                            viewModel.reset()
                        },
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = it,
                        icon = Icons.Filled.Info
                    )
                }


                KingTextTitle(text = stringResource(id = R.string.signup))

                KingTextField(
                    value = viewModel.formState.name.field,
                    label = R.string.name,
                    placeholder = R.string.hint_name,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.name.error?.value
                ) {
                    viewModel.updateName(it)
                }

                KingTextField(
                    value = viewModel.formState.email.field,
                    label = R.string.email,
                    placeholder = R.string.hint_email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.email.error?.value
                ) {
                    viewModel.updateEmail(it)
                }

                KingTextField(
                    value = viewModel.formState.password.field,
                    label = R.string.password,
                    placeholder = R.string.hint_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.password.error?.value,
                    obfuscate = passwordHidden,
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val image = if (passwordHidden) {
                                painterResource(R.drawable.visibility_off_24dp)
                            } else {
                                painterResource(R.drawable.visibility_24dp)
                            }
                            val description = if (passwordHidden) {
                                stringResource(id = R.string.show_password)
                            } else {
                                stringResource(id = R.string.hide_password)
                            }

                            Icon(
                                painter = image,
                                contentDescription = description
                            )
                        }
                    }
                ) {
                    viewModel.updatePassword(it)
                }

                KingTextField(
                    value = viewModel.formState.confirmPassword.field,
                    label = R.string.confirm_password,
                    placeholder = R.string.hint_confirm_password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    obfuscate = confirmPasswordHidden,
                    error = viewModel.formState.confirmPassword.error?.value,
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordHidden = !confirmPasswordHidden }) {
                            val image = if (confirmPasswordHidden) {
                                painterResource(R.drawable.visibility_off_24dp)
                            } else {
                                painterResource(R.drawable.visibility_24dp)
                            }
                            val description = if (confirmPasswordHidden) {
                                stringResource(id = R.string.show_password)
                            } else {
                                stringResource(id = R.string.hide_password)
                            }

                            Icon(
                                painter = image,
                                contentDescription = description
                            )
                        }
                    }
                ) {
                    viewModel.updatePasswordConfirm(it)
                }


                KingTextField(
                    value = TextFieldValue(
                        text = viewModel.formState.document.field,
                        selection = TextRange(viewModel.formState.document.field.length)
                    ),
                    label = R.string.document,
                    placeholder = R.string.hint_document,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    error = viewModel.formState.document.error?.value
                ) { textFieldValue ->
                    viewModel.updateDocument(textFieldValue.text)
                }

                KingTextField(
                    value = TextFieldValue(
                        text = viewModel.formState.birthday.field,
                        selection = TextRange(viewModel.formState.birthday.field.length)
                    ),
                    label = R.string.birthday,
                    placeholder = R.string.hint_birthday,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    error = viewModel.formState.birthday.error?.value

                ) { textFieldValue ->
                    viewModel.updateBirthday(textFieldValue.text)
                }


                KingButton(
                    text = stringResource(id = R.string.sign_up),
                    enabled = viewModel.formState.formIsValid,
                    loading = uiState.isLoading
                ) {
                    viewModel.send()
                }


            }

            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                painter = painterResource(id = R.drawable.cover3),
                contentDescription = stringResource(id = R.string.hamburguer)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightSignUpScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        SignUpScreen(onNavigationClick = {}, onNavigateToLogin = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkLSignUpScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        SignUpScreen(onNavigationClick = {}, onNavigateToLogin = {})
    }
}
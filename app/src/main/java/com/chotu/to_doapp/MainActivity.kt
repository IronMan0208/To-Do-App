package com.chotu.to_doapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chotu.to_doapp.database.DatabaseProvider
import com.chotu.to_doapp.repository.TaskRepository
import com.chotu.to_doapp.ui.theme.ToDoAppTheme
import com.chotu.to_doapp.viewmodel.TaskViewModel
import com.chotu.to_doapp.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                ToDoApp()
            }
        }
    }
}

@Composable
fun ToDoApp() {
    var task by remember { mutableStateOf("") }

    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val repository = remember { TaskRepository(database.taskDao()) }
    val factory = remember { TaskViewModelFactory(repository) }
    val viewModel: TaskViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) { viewModel.loadTasks() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1A0533), Color(0xFF0D1B2A), Color(0xFF0A1628)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            // Header
            Column {
                Text(
                    "My Tasks ✦",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF0EAFF),
                    letterSpacing = (-0.5).sp
                )
                Text(
                    "Stay on top of your day",
                    fontSize = 13.sp,
                    color = Color(0x59FFFFFF)
                )
            }

            // Input Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x0FFFFFFF)),
                border = BorderStroke(1.dp, Color(0x1AFFFFFF)),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = task,
                        onValueChange = { task = it },
                        placeholder = { Text("Add a new task...", color = Color(0x40FFFFFF)) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF7C3AED),
                            unfocusedBorderColor = Color(0x1AFFFFFF),
                            focusedContainerColor = Color(0x14FFFFFF),
                            unfocusedContainerColor = Color(0x0DFFFFFF),
                            focusedTextColor = Color(0xFFE8E0FF),
                            unfocusedTextColor = Color(0xFFE8E0FF),
                            cursorColor = Color(0xFF7C3AED)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF7C3AED), Color(0xFF4F46E5))
                                )
                            )
                            .clickable {
                                if (task.isNotEmpty()) {
                                    viewModel.insertTask(task)
                                    task = ""
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }
            }

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val total = viewModel.tasks.size
                val done = viewModel.tasks.count { it.isCompleted }
                val pending = total - done

                listOf(
                    "Total" to total,
                    "Done" to done,
                    "Pending" to pending
                ).forEach { (label, count) ->
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0x0DFFFFFF)),
                        border = BorderStroke(1.dp, Color(0x14FFFFFF)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "$count",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC4B5FD)
                            )
                            Text(
                                label.uppercase(),
                                fontSize = 10.sp,
                                color = Color(0x59FFFFFF),
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }

            // Task List
            Text(
                "TASKS",
                fontSize = 11.sp,
                color = Color(0x4DFFFFFF),
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Medium
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                viewModel.tasks.forEach { taskItem ->
                    TaskCard(
                        title = taskItem.title,
                        isCompleted = taskItem.isCompleted,
                        onToggle = {
                            viewModel.updateTask(taskItem.copy(isCompleted = !taskItem.isCompleted))
                        },
                        onDelete = { viewModel.deleteTask(taskItem) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// TaskCard Composable
@Composable
fun TaskCard(
    title: String,
    isCompleted: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (isCompleted) 0.45f else 1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x0DFFFFFF)),
        border = BorderStroke(1.dp, Color(0x14FFFFFF)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Checkbox
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isCompleted) Color(0xFF7C3AED) else Color.Transparent
                    )
                    .border(
                        1.5.dp,
                        if (isCompleted) Color(0xFF7C3AED) else Color(0x33FFFFFF),
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onToggle() },
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            // Title
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isCompleted) Color(0x4DFFFFFF) else Color(0xCCFFFFFF),
                textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier.weight(1f)
            )

            // Delete Button
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x1FEF4444))
                    .clickable { onDelete() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFF87171),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ToDoApp1() {

    var task by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val database = remember {
        DatabaseProvider.getDatabase(context)
    }
    val repository = remember {
        TaskRepository(database.taskDao())
    }
    val factory = remember {
        TaskViewModelFactory(repository)
    }
    val viewModel: TaskViewModel = viewModel(
        factory = factory
    )

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "To-Do App",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "${viewModel.tasks.size} Tasks"
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = task,
            onValueChange = {
                task = it
            },
            label = {
                Text("Task")
            })

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            if (task.isNotEmpty()) {
                viewModel.insertTask(task)
                task = ""
            }
        }) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(viewModel.tasks.size) { index ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        viewModel.tasks[index].title,
                        fontSize = 20.sp
                    )

                    Button(
                        onClick = {

                            viewModel.deleteTask(
                                viewModel.tasks[index]
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {

                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        ToDoApp1()
    }
}
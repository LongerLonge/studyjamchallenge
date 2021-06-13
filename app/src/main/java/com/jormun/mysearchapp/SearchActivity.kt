package com.jormun.mysearchapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jormun.mysearchapp.ui.theme.REDBCB7
import com.jormun.mysearchapp.ui.theme.YellowFE390

class SearchActivity : ComponentActivity() {
    val searchVM: SearchViewModel by viewModels()
    val TAG: String = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySearchApp {
                DataArea(sourceList = searchVM.stringOrginData,
                    filterList = searchVM.stringFilterData,
                    { searchVM.saveKeyWord(it) },
                    { searchVM.addStringData(it) },
                    {
                        var result = searchVM.doSearch(searchVM.searchKeyWord)
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }
}

@Composable
fun MySearchApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = Color.White) {
            content()
        }
    }
}

@Composable
fun DataArea(
    sourceList: List<String>,
    filterList: List<String>,
    onTextChanged: (String) -> Unit,
    addCountAndData: (Int) -> Unit,
    searchMethod: () -> Unit
) {
    val countState = remember {
        mutableStateOf(0)
    }
    var inputText by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxHeight()) {
        SearchKeyWordInputView(inputText, onTextChanged = {
            inputText = it
            onTextChanged(inputText)
        }, Modifier.fillMaxWidth())
        AddButton(
            count = countState.value,
            addCountAndData = {
                countState.value = it
                addCountAndData(it)
            },
            Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        )
        SearchButton(
            searchMethod = searchMethod,
            Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        )
        Row(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        ) {
            Column(Modifier.weight(1f)) {
                Text(text = "源数据")
                StringListView(
                    strList = sourceList,
                    modifier = Modifier.fillMaxWidth(),
                    YellowFE390
                )
            }
            Divider(color = Color.Yellow, modifier = Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(text = "搜索结果")
                StringListView(
                    strList = filterList,
                    modifier = Modifier.fillMaxWidth(),
                    REDBCB7
                )
            }
        }
    }
}

@Composable
fun AddButton(count: Int, addCountAndData: (Int) -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = { addCountAndData(count + 1) }, modifier = modifier) {
        Text(text = "添加了${count}条数据")
    }
}

@Composable
fun SearchButton(searchMethod: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = { searchMethod() }, modifier = modifier) {
        Text(text = "开始搜索")
    }
}

@Composable
fun SearchKeyWordInputView(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        label = { Text(text = "搜索") }, modifier = modifier
    )
}

@Composable
fun StringListView(strList: List<String>, modifier: Modifier = Modifier, color: Color) {
    LazyColumn(modifier = modifier) {
        items(items = strList) { strData ->
            Divider(color = Color.Black)
            DataItem(strData = strData, color)
        }
    }
}

@Composable
fun DataItem(strData: String, color: Color) {
    Surface(color = color, modifier = Modifier.fillMaxWidth()) {
        Text(text = strData, Modifier.padding(24.dp))
    }
}

@Preview
@Composable
fun DefaultPreView() {
    val strList = List(1000) { "this is data in: $it" }
    MySearchApp {
        DataArea(
            sourceList = strList,
            filterList = strList,
            onTextChanged = {},
            addCountAndData = {},
            searchMethod = {})
    }
}
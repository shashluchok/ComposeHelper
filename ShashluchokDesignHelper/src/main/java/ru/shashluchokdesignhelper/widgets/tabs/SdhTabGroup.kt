package ru.shashluchokdesignhelper.widgets.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.collections.immutable.ImmutableList
import ru.shashluchokdesignhelper.CustomRippleColor

private const val tabRoundingsPercentage = 50
private const val indicatorZIndex = -1f
private const val tabRippleAlpha = 0.12f
private val tabHorizontalPadding = 12.dp
private val tabVerticalPadding = 8.dp
private val tabHorizontalMargin = 8.dp
private val tabGroupEdgePadding = 16.dp

@Composable
fun SdhTabGroup(
    modifier: Modifier = Modifier,
    tabItems: ImmutableList<String>,
    selectedTabIndex: Int?,
    onTabSelected: (tabIndex: Int) -> Unit,
) {
    val actualSelectedTabIndex = selectedTabIndex ?: 0
    ScrollableTabRow(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = tabVerticalPadding),
        selectedTabIndex = actualSelectedTabIndex,
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(
                        tabPositions[actualSelectedTabIndex]
                    )
                    .fillMaxHeight()
                    .zIndex(indicatorZIndex)
                    .padding(
                        horizontal = tabHorizontalMargin / 2
                    )
                    .alpha(selectedTabIndex?.let { 1f } ?: 0f)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(tabRoundingsPercentage)
                    )
            )
        },
        divider = { },
        edgePadding = tabGroupEdgePadding - tabHorizontalMargin / 2,
    ) {
        tabItems.forEachIndexed { index, tabText ->
            SdhTab(
                modifier = Modifier.padding(
                    horizontal = tabHorizontalMargin / 2
                ),
                onClick = {
                    onTabSelected(index)
                },
                isSelected = selectedTabIndex == index,
                isClickable = selectedTabIndex != null,
                text = tabText
            )
        }
    }
}

/**
 * Can't use androidx.compose.material3.Tab because it's impossible
 * out of the box to set the desired tab width and height.
 */
@Composable
private fun SdhTab(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    isClickable: Boolean,
) {
    CustomRippleColor(MaterialTheme.colorScheme.secondary.copy(alpha = tabRippleAlpha)) {
        Text(
            modifier = modifier
                .clip(RoundedCornerShape(tabRoundingsPercentage))
                .clickable(
                    enabled = isClickable,
                    onClick = onClick
                )
                .padding(
                    vertical = tabVerticalPadding,
                    horizontal = tabHorizontalPadding
                ),
            color = if (isSelected) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.secondary
            },
            text = text,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
}

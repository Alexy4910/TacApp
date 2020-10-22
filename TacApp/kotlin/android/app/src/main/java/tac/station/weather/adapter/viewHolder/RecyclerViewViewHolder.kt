/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tac.station.weather.adapter.viewHolder

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import tac.station.weather.adapter.RecyclerViewAdapter
import tac.station.weather.databinding.WeatherRecyclerViewItemBinding
import tac.station.weather.model.Meteo
import tac.station.weather.setupRecyclerView.WeatherSwipeActionDrawable
import tac.station.weather.setupRecyclerView.ReboundingSwipeActionCallback
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class RecyclerViewViewHolder(
        private val binding: WeatherRecyclerViewItemBinding,
        listener: RecyclerViewAdapter.RecyclerViewAdapterListener
): RecyclerView.ViewHolder(binding.root), ReboundingSwipeActionCallback.ReboundableViewHolder {

    private val starredCornerSize = 24

    override val reboundableView: View = binding.cardView

    init {
        binding.run {
            this.listener = listener
            root.background = WeatherSwipeActionDrawable(root.context)
        }
    }

    fun bind(meteo: Meteo) {
        binding.meteo = meteo
        binding.root.isActivated = meteo.getIsFavorite()

        val interpolation = if (meteo.getIsFavorite()) 3F else 0F
        updateCardViewTopLeftCornerSize(interpolation)

        binding.executePendingBindings()

    }

    override fun onReboundOffsetChanged(
            currentSwipePercentage: Float,
            swipeThreshold: Float,
            currentTargetHasMetThresholdOnce: Boolean
    ) {
        // Only alter shape and activation in the forward direction once the swipe
        // threshold has been met. Undoing the swipe would require releasing the item and
        // re-initiating the swipe.
        if (currentTargetHasMetThresholdOnce) return

        val isStarred = binding.meteo?.getIsFavorite() ?: false

        // Animate the top left corner radius of the email card as swipe happens.
        val interpolation = (currentSwipePercentage / swipeThreshold).coerceIn(0F, 1F)
        val adjustedInterpolation = abs((if (isStarred) 3F else 0F) - interpolation)
        updateCardViewTopLeftCornerSize(adjustedInterpolation)

        // Start the background animation once the threshold is met.
        val thresholdMet = currentSwipePercentage >= swipeThreshold
        val shouldStar = when {
            thresholdMet && isStarred -> false
            thresholdMet && !isStarred -> true
            else -> return
        }
        binding.root.isActivated = shouldStar
    }

    override fun onRebounded() {
        val meteo = binding.meteo ?: return
        binding.listener?.onEmailStarChanged(meteo, !meteo.getIsFavorite())
    }

    // We have to update the shape appearance itself to have the MaterialContainerTransform pick up
    // the correct shape appearance, since it doesn't have access to the MaterialShapeDrawable
    // interpolation. If you don't need this work around, prefer using MaterialShapeDrawable's
    // interpolation property, or in the case of MaterialCardView, the progress property.
    private fun updateCardViewTopLeftCornerSize(interpolation: Float) {
        binding.cardView.apply {
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                    .setTopLeftCornerSize(interpolation * starredCornerSize)
                    .build()
        }
    }
}
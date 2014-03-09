class AddIdCategoryAndPlaceIdToEvent < ActiveRecord::Migration
  def change
  	add_column :events, :id_category, :integer
  	add_column :events, :place_id, :integer, null:false
  end
end

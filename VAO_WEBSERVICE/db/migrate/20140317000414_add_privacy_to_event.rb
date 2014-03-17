class AddPrivacyToEvent < ActiveRecord::Migration
  def change
  	add_column :events, :privacy_type, :string, null:false
  end
end

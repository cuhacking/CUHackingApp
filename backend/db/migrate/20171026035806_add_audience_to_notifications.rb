class AddAudienceToNotifications < ActiveRecord::Migration[5.1]
  def change
    add_column :notifications, :audience, :string
  end
end

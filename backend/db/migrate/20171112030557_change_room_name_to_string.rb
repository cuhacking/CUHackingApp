class ChangeRoomNameToString < ActiveRecord::Migration[5.1]
  def change
    change_column :rooms, :name, :string
  end
end
